package kusitms.domain.scrap.service;

import kusitms.domain.item.entity.Item;
import kusitms.domain.item.repository.ItemRepository;
import kusitms.domain.scrap.entity.Scrap;
import kusitms.domain.scrap.repository.ScrapRepository;
import kusitms.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
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

        validationExistingScrap(item.getId());
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

    private void validationExistingScrap(Long itemId) {
        if (scrapRepository.findAll().isEmpty() != true) {
            List<Scrap> scraps = scrapRepository.findAll();
            for (Scrap scrap : scraps) {
                if(scrap.getItem().getId() == itemId){
                    throw new IllegalArgumentException("이미 스크랩한 상품입니다."); } } }
    }
}
