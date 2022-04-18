package eightseconds.domain.item.service;

import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.category.entity.Category;
import eightseconds.domain.category.service.CategoryService;
import eightseconds.domain.chatroom.service.ChatRoomService;
import eightseconds.domain.item.constant.ItemConstants.EItemServiceImpl;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.*;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.*;
import eightseconds.domain.item.repository.ItemRepository;
import eightseconds.domain.myfile.service.MyFileService;
import eightseconds.domain.notification.service.NotificationService;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final MyFileService myFileService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final ChatRoomService chatRoomService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public RegisterItemResponse saveItem(Long userId, @Valid RegisterItemRequest registerItemRequest){
        validateCreateSoldOutTime(registerItemRequest.getAuctionClosingDate());
        User user = this.userService.getUserByUserId(userId);
        Item item = registerItemRequest.toEntity();
        Category category = this.categoryService.getCategoryByEItemCategory(registerItemRequest.getCategory());
        item.setCategory(category);
        item.setUser(user);
        List<MultipartFile> files = registerItemRequest.getFiles();
        item.addFiles(this.myFileService.saveImages(files));
        this.itemRepository.save(item);
        return RegisterItemResponse.from(item);
    }

    @Override
    @Transactional
    public void deleteByItemId(Long id) {
        validateItemId(id);
        this.itemRepository.deleteById(id);
    }

    @Override
    public ItemDetailsResponse getItem(Long id) {
        validateItemId(id);
        return ItemDetailsResponse.from(this.itemRepository.getById(id));
    }

    @Override
    public Item getItemByItemId(Long id) {
        validateItemId(id);
        return this.itemRepository.getById(id);
    }

    @Override
    public PaginationDto<List<ItemDetailsResponse>> getAllItemsByItemSoldStatus(Pageable pageable, String itemSoldStatus) {
        validateItemSoldStatus(itemSoldStatus);
        Page<Item> page = this.itemRepository.findAllByStatus(pageable, EItemSoldStatus.from(itemSoldStatus));
        List<ItemDetailsResponse> data = page.get().map(ItemDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public List<BestItemResponse> getAllBestItemsByItemSoldStatus(String itemSoldStatus) {
        validateItemSoldStatus(itemSoldStatus);
        List<Item> items = this.itemRepository.findAllItemsByStatus(EItemSoldStatus.from(itemSoldStatus));
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
        Category returnCategory = this.categoryService.getCategoryByEItemCategory(ECategory.from(category));
        validateExistingItemsByCategory(pageable, returnCategory.getId());
        Page<Item> page = this.itemRepository.findAllByCategoryId(pageable, returnCategory.getId());
        List<ItemDetailsResponse> data = page.get().map(ItemDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public PaginationDto<List<ItemDetailsResponse>> getAllItemsOfUser(Pageable pageable, ItemOfUserRequest itemOfUserRequest) {
        User user = this.userService.validateUserId(itemOfUserRequest.getUserId());
        validateItemSoldStatus(itemOfUserRequest.getSoldStatus().toString());
        Page<Item> page = this.itemRepository.findAllByStatusAndUserId(pageable, itemOfUserRequest.getSoldStatus(), user.getId());
        List<ItemDetailsResponse> data = page.get().map(ItemDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    @Transactional
    public SoldResponse soldOutItem(SoldRequest soldRequest) throws IOException {
        Item item = getItemByItemId(soldRequest.getItemId());
        validateItemId(item.getId());
        validateSoldStatusByItemId(item.getId());
        validateSoldOutTime(item.getAuctionClosingDate());
        item.setSoldStatus(EItemSoldStatus.eSoldOut);
        PriceSuggestion priceSuggestion = item.getMaximumPriceEntity();
        priceSuggestion.setAcceptState(true);
        item.setSoldPrice(priceSuggestion.getSuggestionPrice());

        this.notificationService.sendMessageToBidder(priceSuggestion);
        this.notificationService.sendMessageToSeller(item);

        this.chatRoomService.createChatRoom(priceSuggestion, item);

        return SoldResponse.from(item, priceSuggestion);
    }

    /**
     * validate
     */

    private void validateItemSoldStatus(String itemSoldStatus) {
        Arrays.stream(EItemSoldStatus.values())
                .filter(eItemSoldStatus -> itemSoldStatus.equals(eItemSoldStatus.name()))
                .findAny()
                .orElseThrow(() -> new InvalidItemSoldStatusException(EItemServiceImpl.eInvalidItemSoldStatusExceptionMessage.getValue()));
    }

    private void validateCategory(String category) {
        Arrays.stream(ECategory.values())
                .filter(eItemCategory -> category.equals(eItemCategory.name()))
                .findAny()
                .orElseThrow(() -> new InvalidCategoryException(EItemServiceImpl.eInvalidCategoryExceptionMessage.getValue()));
    }

    private void validateExistingItemsByCategory(Pageable pageable, Long categoryId) {
        this.itemRepository.findAllByCategoryId(pageable, categoryId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundItemException(EItemServiceImpl.eNotFoundItemExceptionForCategoryMessage.getValue()));
    }

    @Override
    public Item validateItemId(Long itemId) {
        return this.itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundItemException(EItemServiceImpl.eNotFoundItemExceptionForDefaultMessage.getValue()));
    }

    @Override
    public void validateSoldStatusByItemId(Long itemId) {
        Optional<Item> item = this.itemRepository.findById(itemId);
        if (!item.get().getSoldStatus().equals(EItemSoldStatus.eOnGoing)) {
            throw new NotOnGoingException(EItemServiceImpl.eNotOnGoingExceptionMessage.getValue());
        }
    }

    @Override
    public void validateSoldOutTime(LocalDateTime auctionClosingDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isBefore(auctionClosingDate)) {
            throw new NotSoldOutTimeException(EItemServiceImpl.eNotSoldOutTimeExceptionMessage.getValue());
        }
    }

    private void validateCreateSoldOutTime(LocalDateTime auctionClosingDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isAfter(auctionClosingDate)) {
            throw new NotDesirableAuctionEndTimeException(EItemServiceImpl.eNotDesirableAuctionEndTimeExceptionMessage.getValue());
        }
    }

    private void validatePriceSuggestion(Optional<PriceSuggestion> priceSuggestion) {
        if(priceSuggestion.isEmpty())
            throw new NotPriceSuggestionContentException(EItemServiceImpl.eNotPriceSuggestionContentExceptionMessage.getValue());
    }
}
