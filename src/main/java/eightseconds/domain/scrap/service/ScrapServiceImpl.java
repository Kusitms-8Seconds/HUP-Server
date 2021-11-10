package eightseconds.domain.scrap.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.repository.ItemRepository;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.scrap.repository.ScrapRepository;
import eightseconds.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScrapServiceImpl implements ScrapService{

    private final ScrapRepository scrapRepository;
    private final ItemRepository itemRepository;

    @Override
    public Scrap saveScrap(User user, Item item) {

        validationExistingScrap(user, item.getId());
        validationIsExistingItem(item.getId());
        Scrap scrap = Scrap.builder()
                .user(user)
                .item(item)
                .build();
        scrapRepository.save(scrap);
        return null;
    }

    @Override
    public void deleteScrap(User user, Item item, Long deleteScrapId) {
        validationIsExistingScrap(deleteScrapId);
        Scrap scrap = scrapRepository.findById(deleteScrapId).get();
        scrap.setUser(null);
        scrap.setItem(null);
        scrapRepository.deleteById(deleteScrapId);
    }

    @Override
    public Page<Scrap> getAllScrapsByUserId(Pageable pageable, Long userId) {
        validationExistingScrapByUserId(pageable, userId);
        Page<Scrap> scraps = scrapRepository.findAllByUserId(pageable, userId);
        return scraps;
    }

    @Override
    public Long getAllScrapsByItemIdQuantity(Long id) {
        Long itemCountByItemId = scrapRepository.findItemCountByItemId(id);
        return itemCountByItemId;
    }

    /**
     * validation
     */

    private void validationIsExistingItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isEmpty() == true){
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    private void validationIsExistingScrap(Long deleteScrapId) {
        Optional<Scrap> scrap = scrapRepository.findById(deleteScrapId);
        if(scrap.isEmpty() == true){
            throw new IllegalArgumentException("존재하지 않는 스크랩입니다.");
        }
    }

    private void validationExistingScrap(User user, Long itemId) {
        if (scrapRepository.findAll().isEmpty() != true) {
            List<Scrap> scraps = scrapRepository.findAllByUserId(user.getId());
            for (Scrap scrap : scraps) {
                if(scrap.getItem().getId() == itemId){
                    throw new IllegalArgumentException("이미 스크랩한 상품입니다."); } } }
    }

    private void validationExistingScrapByUserId(Pageable pageable, Long userId) {
        if (scrapRepository.findAllByUserId(pageable, userId).isEmpty() == true) {
            throw new IllegalArgumentException("해당 유저의 스크랩 내역이 존재하지 않습니다.");
        }
    }
}
