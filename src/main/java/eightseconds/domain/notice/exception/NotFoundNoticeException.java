package eightseconds.domain.notice.exception;

import static eightseconds.domain.notice.constant.NoticeConstants.NoticeExceptionList.NOT_FOUND_NOTICE;

public class NotFoundNoticeException extends NoticeException {
    public NotFoundNoticeException() {
        super(NOT_FOUND_NOTICE.getErrorCode(),
                NOT_FOUND_NOTICE.getHttpStatus(),
                NOT_FOUND_NOTICE.getMessage()
        );
    }
}
