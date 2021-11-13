package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.dto.RegisterItemRequest;
import eightseconds.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface ItemService{

    Item saveItem(String loginId, @Valid RegisterItemRequest registerItemRequest);
    void addFiles(Item item, List<MyFile> saveFiles);
    void deleteByItemId(Long id);
    void validationItemId(Long id);
    Item getItem(Long id);
    void validationUserAndItem(List<Item> items, Long id);
    Page<Item> getItemsByUserId(Pageable pageable, Long userId);
    Page<Item> getItemsByCategory(Pageable pageable, EItemCategory category);
    void validationSoldStatusByItemId(Long itemId);
    Page<Item> getItemsByStatusAndUserId(Pageable pageable, ItemConstants.EItemSoldStatus soldStatus, Long userId);
}