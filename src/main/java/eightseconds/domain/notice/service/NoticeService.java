package eightseconds.domain.notice.service;

import eightseconds.domain.notice.dto.NoticeListResponse;
import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.dto.UpdateNoticeResponse;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public interface NoticeService {
    NoticeResponse createNotice(String userId, String title, String body, @Nullable List<MultipartFile> files) throws IOException;
    void deleteNotice(Long noticeId);
    UpdateNoticeResponse updateNotice(String userId, String noticeId, String title, String body, List<MultipartFile> files) throws IOException;
    PaginationDto<List<NoticeListResponse>> getAllNotices(Pageable pageable);
    NoticeResponse getNotice(Long noticeId);

}
