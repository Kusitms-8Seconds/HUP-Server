package eightseconds.domain.notice.service;

import eightseconds.domain.notice.dto.NoticeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoticeService {
    NoticeResponse createNotice(Long userId, String title, String body, List<MultipartFile> files) throws IOException;
}
