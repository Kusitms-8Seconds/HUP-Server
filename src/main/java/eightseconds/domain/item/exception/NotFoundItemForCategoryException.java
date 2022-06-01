package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_FOUND_ITEM_FOR_CATEGORY;

public class NotFoundItemForCategoryException extends ItemException {
    public NotFoundItemForCategoryException() {
        super(NOT_FOUND_ITEM_FOR_CATEGORY.getErrorCode(),
                NOT_FOUND_ITEM_FOR_CATEGORY.getHttpStatus(),
                NOT_FOUND_ITEM_FOR_CATEGORY.getMessage()
        );
    }
}
