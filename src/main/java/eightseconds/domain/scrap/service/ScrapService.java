package eightseconds.domain.scrap.service;

import eightseconds.domain.scrap.dto.*;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScrapService {
    ScrapRegisterResponse saveScrap(ScrapRegisterRequest scrapRegisterRequest);
    PaginationDto<List<ScrapDetailsResponse>> getAllScrapsByUserId(Pageable pageable, Long userId);
    ScrapCountResponse getAllScraps(Long id);
    ScrapCheckedResponse isCheckedScrap(ScrapCheckedRequest scrapCheckedRequest);
    void deleteScrap(Long scrapId);
}
