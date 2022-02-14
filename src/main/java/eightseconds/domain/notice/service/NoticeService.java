package eightseconds.domain.notice.service;

import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.dto.UpdateNoticeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    NoticeResponse createNotice(String userId, String title, String body, List<MultipartFile> files) throws IOException;
    void deleteNotice(Long noticeId);
    UpdateNoticeResponse updateNotice(String userId, String noticeId, String title, String body, List<MultipartFile> files) throws IOException;

}
