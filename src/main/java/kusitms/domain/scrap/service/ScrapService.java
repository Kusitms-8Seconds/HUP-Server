package kusitms.domain.scrap.service;

import kusitms.domain.item.entity.Item;
import kusitms.domain.scrap.entity.Scrap;
import kusitms.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScrapService {
    Scrap saveScrap(User user, Item item);
    void deleteScrap(User user, Item item, Long deleteScrapId);
    Page<Scrap> getAllScrapsByUserId(Pageable pageable, Long userId);
}
