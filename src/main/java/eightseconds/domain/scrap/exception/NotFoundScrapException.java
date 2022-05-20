package eightseconds.domain.scrap.exception;

import static eightseconds.domain.scrap.constant.ScrapConstants.ScrapExceptionList.NOT_FOUND_SCRAP;

public class NotFoundScrapException extends ScrapException {
    public NotFoundScrapException() {
        super(NOT_FOUND_SCRAP.getErrorCode(),
                NOT_FOUND_SCRAP.getHttpStatus(),
                NOT_FOUND_SCRAP.getMessage()
        );
    }
}
