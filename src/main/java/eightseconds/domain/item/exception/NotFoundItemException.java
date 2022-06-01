package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_FOUND_ITEM;

public class NotFoundItemException extends ItemException {
    public NotFoundItemException() {
        super(NOT_FOUND_ITEM.getErrorCode(),
                NOT_FOUND_ITEM.getHttpStatus(),
                NOT_FOUND_ITEM.getMessage()
        );
    }
}
