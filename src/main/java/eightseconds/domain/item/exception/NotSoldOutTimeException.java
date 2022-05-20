package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_SOLD_OUT_TIME;

public class NotSoldOutTimeException extends ItemException {
    public NotSoldOutTimeException() {
        super(NOT_SOLD_OUT_TIME.getErrorCode(),
                NOT_SOLD_OUT_TIME.getHttpStatus(),
                NOT_SOLD_OUT_TIME.getMessage()
        );
    }
}
