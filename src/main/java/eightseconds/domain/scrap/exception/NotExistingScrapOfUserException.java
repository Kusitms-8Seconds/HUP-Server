package eightseconds.domain.scrap.exception;

import static eightseconds.domain.scrap.constant.ScrapConstants.ScrapExceptionList.NOT_EXISTING_SCRAP_OF_USER;

public class NotExistingScrapOfUserException extends ScrapException {
    public NotExistingScrapOfUserException() {
        super(NOT_EXISTING_SCRAP_OF_USER.getErrorCode(),
                NOT_EXISTING_SCRAP_OF_USER.getHttpStatus(),
                NOT_EXISTING_SCRAP_OF_USER.getMessage()
        );
    }
}
