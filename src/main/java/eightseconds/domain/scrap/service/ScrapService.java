package eightseconds.domain.scrap.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScrapService {
    Scrap saveScrap(User user, Item item);
    void deleteScrap(User user, Item item, Long deleteScrapId);
    Page<Scrap> getAllScrapsByUserId(Pageable pageable, Long userId);
    Long getAllScrapsByItemIdQuantity(Long id);

}
