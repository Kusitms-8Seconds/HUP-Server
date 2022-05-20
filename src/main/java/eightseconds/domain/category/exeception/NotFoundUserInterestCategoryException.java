package eightseconds.domain.category.exeception;

import static eightseconds.domain.category.constant.CategoryConstants.CategoryExceptionList.NOT_FOUND_USER_INTEREST_CATEGORY;

public class NotFoundUserInterestCategoryException extends CategoryException {
    public NotFoundUserInterestCategoryException() {
        super(NOT_FOUND_USER_INTEREST_CATEGORY.getErrorCode(),
                NOT_FOUND_USER_INTEREST_CATEGORY.getHttpStatus(),
                NOT_FOUND_USER_INTEREST_CATEGORY.getMessage()
        );
    }
}
