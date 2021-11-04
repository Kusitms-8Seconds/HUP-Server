package eightseconds.domain.item.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.dto.RegisterItemRequest;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.repository.ItemRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Item saveItem(String loginId, @Valid RegisterItemRequest registerItemRequest) {
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

    /**
     * validation
     */
    @Override
    public void validationItemId(Long itemId){
        if (itemRepository.findById(itemId).isEmpty()) {
            throw new IllegalArgumentException("해당 아이디로 상품을 찾을 수 없습니다."); }
    }

    @Override
    public void validationUserAndItem(List<Item> items, Long id) {
        boolean check = false;
        for (Item item : items) {
            if(item.getId() == id){
                check = true; } }
        if (check == false) {
            throw new IllegalArgumentException("해당 상품에 대한 유저 권한이 없습니다.");
        }
    }

    private void validationExistingItemsByCategory(Pageable pageable, EItemCategory category) {
       if(itemRepository.findAllByCategory(pageable, category).isEmpty()){
           throw new IllegalArgumentException("해당 카테고리에 해당하는 상품이 없습니다."); }
    }
}
