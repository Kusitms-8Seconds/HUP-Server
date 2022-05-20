package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_ON_GOING;

public class NotOnGoingException extends ItemException {
    public NotOnGoingException() {
        super(NOT_ON_GOING.getErrorCode(),
                NOT_ON_GOING.getHttpStatus(),
                NOT_ON_GOING.getMessage()
        );
    }
}
