package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_PRICE_SUGGESTION_CONTENT;

public class NotPriceSuggestionContentException extends ItemException {
    public NotPriceSuggestionContentException() {
        super(NOT_PRICE_SUGGESTION_CONTENT.getErrorCode(),
                NOT_PRICE_SUGGESTION_CONTENT.getHttpStatus(),
                NOT_PRICE_SUGGESTION_CONTENT.getMessage()
        );
    }
}
