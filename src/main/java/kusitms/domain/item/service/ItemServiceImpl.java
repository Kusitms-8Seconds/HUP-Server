package kusitms.domain.item.service;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.item.dto.RegisterItemRequest;
import kusitms.domain.item.entity.Item;
import kusitms.domain.item.repository.ItemRepository;
import kusitms.domain.user.entity.User;
import kusitms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public Item saveItem(String email, @Valid RegisterItemRequest registerItemRequest) {
        Item item = registerItemRequest.toEntity();
        Optional<User> user = userRepository.findUserByEmail(email);
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

    /**
     * validation
     */
    @Override
    public void validationItemId(Long itemId){
        if (itemRepository.findById(itemId).isEmpty() == true) {
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
}
