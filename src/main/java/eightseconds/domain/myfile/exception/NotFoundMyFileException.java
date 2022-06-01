package eightseconds.domain.myfile.exception;


import static eightseconds.domain.notice.constant.NoticeConstants.NoticeExceptionList.NOT_FOUND_NOTICE;

public class NotFoundMyFileException extends MyFileException {
    public NotFoundMyFileException() {
        super(NOT_FOUND_NOTICE.getErrorCode(),
                NOT_FOUND_NOTICE.getHttpStatus(),
                NOT_FOUND_NOTICE.getMessage()
        );
    }
}
