package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.*;
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
    Item validateItemId(Long id);
    ItemDetailsResponse getItem(Long id);
    Item getItemByItemId(Long id);
    void validateUserAndItem(List<Item> items, Long id);
    void validateSoldStatusByItemId(Long itemId);
    PaginationDto<List<ItemDetailsResponse>> getItemsByStatusAndUserId(Pageable pageable, EItemSoldStatus soldStatus, Long userId);
    void validateSoldOutTime(LocalDateTime auctionClosingDate);
    Item soldOutItem(Long itemId);

    PaginationDto<List<ItemDetailsResponse>> getAllItemsByItemSoldStatus(Pageable pageable, String itemSoldStatus);
    List<BestItemResponse> getAllBestItemsByItemSoldStatus(String itemSoldStatus);
    PaginationDto<List<ItemDetailsResponse>> getAllItemsByCategory(Pageable pageable, String category);

}