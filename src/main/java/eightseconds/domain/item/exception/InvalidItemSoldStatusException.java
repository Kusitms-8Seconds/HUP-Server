package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.INVALID_ITEM_SOLD_STATUS;

public class InvalidItemSoldStatusException extends ItemException {
    public InvalidItemSoldStatusException() {
        super(INVALID_ITEM_SOLD_STATUS.getErrorCode(),
                INVALID_ITEM_SOLD_STATUS.getHttpStatus(),
                INVALID_ITEM_SOLD_STATUS.getMessage()
        );
    }
}
