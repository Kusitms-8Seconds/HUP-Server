package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_BID_ITEM;

public class NotBidItemException extends ItemException {
    public NotBidItemException() {
        super(NOT_BID_ITEM.getErrorCode(),
                NOT_BID_ITEM.getHttpStatus(),
                NOT_BID_ITEM.getMessage()
        );
    }
}
