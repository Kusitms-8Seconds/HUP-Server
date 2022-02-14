package eightseconds.domain.notice.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.service.FileService;
import eightseconds.domain.notice.constant.NoticeConstants.ENoticeServiceImpl;
import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.entity.Notice;
import eightseconds.domain.notice.exception.NotFoundNoticeException;
import eightseconds.domain.notice.repository.NoticeRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final UserService userService;
    private final FileService fileService;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public NoticeResponse createNotice(Long userId, String title, String body, List<MultipartFile> files) throws IOException {
        User user = userService.validateUserId(userId);
        Notice notice = Notice.createNotice(user, title, body, files);
        user.addNotice(notice);
        if(!files.isEmpty()){
        List<MyFile> saveFiles = fileService.save(files);
        notice.addFiles(saveFiles);}
        noticeRepository.save(notice);
        return NoticeResponse.from(notice);

    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId) {
        Notice notice = validateNoticeId(noticeId);
        noticeRepository.delete(notice);
    }

    private Notice validateNoticeId(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundNoticeException(ENoticeServiceImpl.eNotFoundNoticeExceptionMessage.getValue()));

    }

}
