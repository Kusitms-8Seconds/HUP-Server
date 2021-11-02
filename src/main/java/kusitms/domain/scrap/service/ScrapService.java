package kusitms.domain.scrap.service;

import kusitms.domain.item.entity.Item;
import kusitms.domain.scrap.entity.Scrap;
import kusitms.domain.user.entity.User;

public interface ScrapService {
    Scrap saveScrap(User user, Item item);
    void deleteScrap(User user, Item item, Long deleteScrapId);
}
