package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.RegisterItemRequest;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.NotOnGoingException;
import eightseconds.domain.item.exception.NotPriceSuggestionContentException;
import eightseconds.domain.item.exception.NotSoldOutTimeException;
import eightseconds.domain.item.repository.ItemRepository;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.repository.PriceSuggestionRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PriceSuggestionRepository priceSuggestionRepository;

    @Override
    @Transactional
    public Item saveItem(String loginId, @Valid RegisterItemRequest registerItemRequest) {
        validationCreateSoldOutTime(registerItemRequest.getAuctionClosingDate());
        Item item = registerItemRequest.toEntity();
        Optional<User> user = userRepository.findUserByLoginId(loginId);
        Item savedItem = itemRepository.save(item);
        savedItem.setUser(user.get());
        return savedItem;
    }

    @Override
    @Transactional
    public void addFiles(Item item, List<MyFile> saveFiles) {
        item.addFiles(saveFiles);
    }

    @Override
    @Transactional
    public void deleteByItemId(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item getItem(Long id) {
        Item item = itemRepository.getById(id);
        return item;
    }

    @Override
    public Page<Item> getItemsByUserId(Pageable pageable, Long userId) {
        return itemRepository.findAllByUserId(pageable, userId);
    }

    @Override
    public Page<Item> getItemsByCategory(Pageable pageable, EItemCategory category) {
        validationExistingItemsByCategory(pageable, category);
        return itemRepository.findAllByCategory(pageable, category);
    }

    @Override
    public Page<Item> getItemsByStatusAndUserId(Pageable pageable, EItemSoldStatus soldStatus, Long userId) {
        return itemRepository.findAllByStatusAndUserId(pageable, soldStatus, userId);
    }

    @Override
    @Transactional
    public Item soldOutItem(Long itemId) {
        Item item = getItem(itemId);
        validationItemId(item.getId());
        validationSoldStatusByItemId(item.getId());
        validationSoldOutTime(item.getAuctionClosingDate());
        item.setSoldStatus(EItemSoldStatus.eSoldOut);
        Optional<PriceSuggestion> priceSuggestion = priceSuggestionRepository.getMaximumPriceByItemId(item.getId());
        validationPriceSuggestion(priceSuggestion);
        priceSuggestion.get().setAcceptState(true);
        item.setSoldPrice(priceSuggestion.get().getSuggestionPrice());
        return item;
    }

    private void validationPriceSuggestion(Optional<PriceSuggestion> priceSuggestion) {
        if(priceSuggestion.isEmpty())
            throw new NotPriceSuggestionContentException("경매입찰내역이 없습니다.");
    }

    /**
     * validation
     */
    @Override
    public void validationItemId(Long itemId) {
        if (itemRepository.findById(itemId).isEmpty()) {
            throw new IllegalArgumentException("해당 아이디로 상품을 찾을 수 없습니다.");
        }
    }

    @Override
    public void validationUserAndItem(List<Item> items, Long id) {
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

    private void validationExistingItemsByCategory(Pageable pageable, EItemCategory category) {
        if (itemRepository.findAllByCategory(pageable, category).isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리에 해당하는 상품이 없습니다.");
        }
    }

    @Override
    public void validationSoldStatusByItemId(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.get().getSoldStatus().equals(EItemSoldStatus.eOnGoing)) {
            throw new NotOnGoingException("경매중인 상품이 아닙니다.");
        }
    }

    @Override
    public void validationSoldOutTime(LocalDateTime auctionClosingDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!currentDateTime.isAfter(auctionClosingDate)) {
            throw new NotSoldOutTimeException("낙찰시간이 아닙니다.");
        }
    }

    private void validationCreateSoldOutTime(LocalDateTime auctionClosingDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (!currentDateTime.isBefore(auctionClosingDate)) {
            throw new NotSoldOutTimeException("경매종료일자가 현재시각보다 빠릅니다.");
        }
    }
}
