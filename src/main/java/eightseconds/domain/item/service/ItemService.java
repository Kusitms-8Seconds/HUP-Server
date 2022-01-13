package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.BestItemResponse;
import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.dto.RegisterItemRequest;
import eightseconds.domain.item.dto.RegisterItemResponse;
import eightseconds.domain.item.entity.Item;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemService{

    RegisterItemResponse saveItem(Long userId, @Valid RegisterItemRequest registerItemRequest) throws IOException;
    void addFiles(Item item, List<MyFile> saveFiles);
    void deleteByItemId(Long id);
    void validateItemId(Long id);
    ItemDetailsResponse getItem(Long id);
    Item getItemByItemId(Long id);
    void validateUserAndItem(List<Item> items, Long id);
    PaginationDto<List<ItemDetailsResponse>> getAllItemsByItemSoldStatus(Pageable pageable, String itemSoldStatus);
    PaginationDto<List<ItemDetailsResponse>> getItemsByCategory(Pageable pageable, EItemCategory category);
    void validateSoldStatusByItemId(Long itemId);
    PaginationDto<List<ItemDetailsResponse>> getItemsByStatusAndUserId(Pageable pageable, EItemSoldStatus soldStatus, Long userId);
    void validateSoldOutTime(LocalDateTime auctionClosingDate);
    Item soldOutItem(Long itemId);

    List<BestItemResponse> getAllBestItems(EItemSoldStatus itemSoldStatus);

}