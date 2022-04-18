package eightseconds.domain.item.service;

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
    void deleteByItemId(Long id);
    Item validateItemId(Long id);
    ItemDetailsResponse getItem(Long id);
    Item getItemByItemId(Long id);
    void validateSoldStatusByItemId(Long itemId);
    PaginationDto<List<ItemDetailsResponse>> getAllItemsOfUser(Pageable pageable, ItemOfUserRequest itemOfUserRequest);
    void validateSoldOutTime(LocalDateTime auctionClosingDate);
    SoldResponse soldOutItem(SoldRequest soldRequest) throws IOException;
    PaginationDto<List<ItemDetailsResponse>> getAllItemsByItemSoldStatus(Pageable pageable, String itemSoldStatus);
    List<BestItemResponse> getAllBestItemsByItemSoldStatus(String itemSoldStatus);
    PaginationDto<List<ItemDetailsResponse>> getAllItemsByCategory(Pageable pageable, String category);

}