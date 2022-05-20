package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_FOUND_ITEM_FOR_DEFALUT;

public class NotFoundItemForDefaultException extends ItemException {
    public NotFoundItemForDefaultException() {
        super(NOT_FOUND_ITEM_FOR_DEFALUT.getErrorCode(),
                NOT_FOUND_ITEM_FOR_DEFALUT.getHttpStatus(),
                NOT_FOUND_ITEM_FOR_DEFALUT.getMessage()
        );
    }
}
