package eightseconds.domain.category.exeception;

import static eightseconds.domain.category.constant.CategoryConstants.CategoryExceptionList.NOT_FOUND_CATEGORY;

public class NotFoundCategoryException extends CategoryException {
    public NotFoundCategoryException() {
        super(NOT_FOUND_CATEGORY.getErrorCode(),
                NOT_FOUND_CATEGORY.getHttpStatus(),
                NOT_FOUND_CATEGORY.getMessage()
        );
    }
}
