package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.service.FileService;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.*;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.*;
import eightseconds.domain.item.repository.ItemRepository;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.repository.PriceSuggestionRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final PriceSuggestionRepository priceSuggestionRepository;

    private final FileService fileService;
    private final UserService userService;

    @Override
    @Transactional
    public RegisterItemResponse saveItem(Long userId, @Valid RegisterItemRequest registerItemRequest) throws IOException {
        validateCreateSoldOutTime(registerItemRequest.getAuctionClosingDate());
        Item savedItem = itemRepository.save(registerItemRequest.toEntity());
        User user = userService.getUserByUserId(userId);
        savedItem.setUser(user);
        if(registerItemRequest.getFiles() != null ){
        List<MyFile> saveFiles = fileService.save(registerItemRequest.getFiles());
        addFiles(savedItem, saveFiles);}
        return RegisterItemResponse.from(savedItem);
    }

    @Override
    @Transactional
    public void addFiles(Item item, List<MyFile> saveFiles) {
        item.addFiles(saveFiles);
    }

    @Override
    @Transactional
    public void deleteByItemId(Long id) {
        validateItemId(id);
        fileService.deleteAllByItemId(id);
        itemRepository.deleteById(id);
    }

    @Override
    public ItemDetailsResponse getItem(Long id) {
        validateItemId(id);
        return ItemDetailsResponse.from(itemRepository.getById(id));
    }

    @Override
    public Item getItemByItemId(Long id) {
        validateItemId(id);
        return itemRepository.getById(id);
    }

    @Override
    public PaginationDto<List<ItemDetailsResponse>> getAllItemsByItemSoldStatus(Pageable pageable, String itemSoldStatus) {
        validateItemSoldStatus(itemSoldStatus);
        Page<Item> page = itemRepository.findAllByStatus(pageable, EItemSoldStatus.from(itemSoldStatus));
        List<ItemDetailsResponse> data = page.get().map(ItemDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public List<BestItemResponse> getAllBestItemsByItemSoldStatus(String itemSoldStatus) {
        validateItemSoldStatus(itemSoldStatus);
        List<Item> items = itemRepository.findAllItemsByStatus(EItemSoldStatus.from(itemSoldStatus));
        List<BestItemResponse> bestItems = new ArrayList<>();
        for (Item item : items) { bestItems.add(BestItemResponse.from(item, item.getScraps().size())); }
        Collections.sort(bestItems, new ItemComparator());
        return bestItems;
    }
    class ItemComparator implements Comparator<BestItemResponse> {
        @Override
        public int compare(BestItemResponse a,BestItemResponse b){
            if(a.getHeart()<b.getHeart()) return 1;
            if(a.getHeart()>b.getHeart()) return -1;
            return 0;
        }
    }

    @Override
    public PaginationDto<List<ItemDetailsResponse>> getAllItemsByCategory(Pageable pageable, String category) {
        validateCategory(category);
        validateExistingItemsByCategory(pageable, EItemCategory.from(category));
        Page<Item> page = itemRepository.findAllByCategory(pageable, EItemCategory.from(category));
        List<ItemDetailsResponse> data = page.get().map(ItemDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public PaginationDto<List<ItemDetailsResponse>> getItemsByStatusAndUserId(Pageable pageable, EItemSoldStatus soldStatus, Long userId) {
        Page<Item> page = itemRepository.findAllByStatusAndUserId(Pageable.ofSize(30), soldStatus, userId);
        List<ItemDetailsResponse> data = page.get().map(ItemDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    @Transactional
    public Item soldOutItem(Long itemId) {
        Item item = getItemByItemId(itemId);
        validateItemId(item.getId());
        validateSoldStatusByItemId(item.getId());
        validateSoldOutTime(item.getAuctionClosingDate());
        item.setSoldStatus(EItemSoldStatus.eSoldOut);
        Optional<PriceSuggestion> priceSuggestion = priceSuggestionRepository.getMaximumPriceByItemId(item.getId());
        validatePriceSuggestion(priceSuggestion);
        priceSuggestion.get().setAcceptState(true);
        item.setSoldPrice(priceSuggestion.get().getSuggestionPrice());
        return item;
    }

    /**
     * validate
     */

    private void validateItemSoldStatus(String itemSoldStatus) {
        Arrays.stream(EItemSoldStatus.values())
                .filter(eItemSoldStatus -> itemSoldStatus.equals(eItemSoldStatus.name()))
                .findAny()
                .orElseThrow(() -> new InvalidItemSoldStatusException("유효하지 않은 상품판매상태입니다."));
    }

    private void validateCategory(String category) {
        Arrays.stream(EItemCategory.values())
                .filter(eItemCategory -> category.equals(eItemCategory.name()))
                .findAny()
                .orElseThrow(() -> new InvalidCategoryException("유효하지 않은 카테고리입니다."));
    }

    private void validateExistingItemsByCategory(Pageable pageable, EItemCategory category) {
        itemRepository.findAllByCategory(pageable, category)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundItemException("해당 카테고리에 해당하는 상품이 없습니다."));
    }

    @Override
    public Item validateItemId(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundItemException("해당 아이디로 상품을 찾을 수 없습니다."));
        return item;
    }

    @Override
    public void validateUserAndItem(List<Item> items, Long id) {
        boolean check = false;
        for (Item item : items) {
            if (item.getId() == id) {
                check = true;
            }
        }
        if (check == false) {
            throw new IllegalArgumentException("해당 상품에 대한 유저 권한이 없습니다.");
        }
    }

    @Override
    public void validateSoldStatusByItemId(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.get().getSoldStatus().equals(EItemSoldStatus.eOnGoing)) {
            throw new NotOnGoingException("경매중인 상품이 아닙니다.");
        }
    }

    @Override
    public void validateSoldOutTime(LocalDateTime auctionClosingDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!currentDateTime.isAfter(auctionClosingDate)) {
            throw new NotSoldOutTimeException("낙찰시간이 아닙니다.");
        }
    }

    private void validateCreateSoldOutTime(LocalDateTime auctionClosingDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!currentDateTime.isBefore(auctionClosingDate)) {
            throw new NotDesirableAuctionEndTimeException("경매종료일자가 현재시각보다 빠릅니다.");
        }
    }

    private void validatePriceSuggestion(Optional<PriceSuggestion> priceSuggestion) {
        if(priceSuggestion.isEmpty())
            throw new NotPriceSuggestionContentException("경매입찰내역이 없습니다.");
    }
}
