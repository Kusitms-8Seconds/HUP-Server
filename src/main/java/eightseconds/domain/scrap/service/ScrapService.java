package eightseconds.domain.scrap.service;

import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.scrap.dto.*;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.user.entity.User;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScrapService {
    ScrapRegisterResponse saveScrap(ScrapRegisterRequest scrapRegisterRequest);
    PaginationDto<List<ScrapDetailsResponse>> getAllScrapsByUserId(Pageable pageable, Long userId);
    Long getAllScrapsByItemIdQuantity(Long id);
    ScrapCheckedResponse isCheckedScrap(ScrapCheckedRequest scrapCheckedRequest);
    void deleteScrap(Long scrapId);
}
