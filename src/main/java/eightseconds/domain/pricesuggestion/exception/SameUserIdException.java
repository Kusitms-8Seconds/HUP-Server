package eightseconds.domain.pricesuggestion.exception;

import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.PRIOR_PRICE_SUGGESTION;
import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.SAME_USER_ID;

public class SameUserIdException extends PriceSuggestionException {
    public SameUserIdException() {
        super(SAME_USER_ID.getErrorCode(),
                SAME_USER_ID.getHttpStatus(),
                SAME_USER_ID.getMessage()
        );
    }
}
