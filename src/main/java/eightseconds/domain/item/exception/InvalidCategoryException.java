package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.INVALID_CATEGORY;

public class InvalidCategoryException extends ItemException {
    public InvalidCategoryException() {
        super(INVALID_CATEGORY.getErrorCode(),
                INVALID_CATEGORY.getHttpStatus(),
                INVALID_CATEGORY.getMessage()
        );
    }
}