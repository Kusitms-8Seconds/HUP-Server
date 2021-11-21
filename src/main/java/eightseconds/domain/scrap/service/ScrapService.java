package eightseconds.domain.scrap.service;

import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.scrap.dto.ScrapCheckedRequest;
import eightseconds.domain.scrap.dto.ScrapCheckedResponse;
import eightseconds.domain.scrap.dto.ScrapDetailsResponse;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.user.entity.User;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScrapService {
    Scrap saveScrap(User user, Item item);
    //void deleteScrap(User user, Item item, Long deleteScrapId);
    PaginationDto<List<ScrapDetailsResponse>> getAllScrapsByUserId(Pageable pageable, Long userId);
    Long getAllScrapsByItemIdQuantity(Long id);
    ScrapCheckedResponse isCheckedScrap(ScrapCheckedRequest scrapCheckedRequest);
    void deleteScrap(Long scrapId);
}
