package eightseconds.domain.scrap.exception;

import static eightseconds.domain.scrap.constant.ScrapConstants.ScrapExceptionList.ALREADY_SCRAP;


public class AlreadyScrapException extends ScrapException {
    public AlreadyScrapException() {
        super(ALREADY_SCRAP.getErrorCode(),
                ALREADY_SCRAP.getHttpStatus(),
                ALREADY_SCRAP.getMessage()
        );
    }
}
