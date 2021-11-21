package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.BestItemResponse;
import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.dto.RegisterItemRequest;
import eightseconds.domain.item.entity.Item;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemService{

    Item saveItem(Long userId, @Valid RegisterItemRequest registerItemRequest);
    void addFiles(Item item, List<MyFile> saveFiles);
    void deleteByItemId(Long id);
    void validationItemId(Long id);
    Item getItem(Long id);
    void validationUserAndItem(List<Item> items, Long id);
    PaginationDto<List<ItemDetailsResponse>> getAllItems(Pageable pageable, EItemSoldStatus itemSoldStatus);
    PaginationDto<List<ItemDetailsResponse>> getItemsByCategory(Pageable pageable, EItemCategory category);
    void validationSoldStatusByItemId(Long itemId);
    PaginationDto<List<ItemDetailsResponse>> getItemsByStatusAndUserId(Pageable pageable, EItemSoldStatus soldStatus, Long userId);
    void validationSoldOutTime(LocalDateTime auctionClosingDate);
    Item soldOutItem(Long itemId);

    List<BestItemResponse> getAllBestItems(EItemSoldStatus itemSoldStatus);

}