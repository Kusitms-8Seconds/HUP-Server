package eightseconds.domain.pricesuggestion.exception;

import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.PRIOR_PRICE_SUGGESTION;

public class PriorPriceSuggestionException extends PriceSuggestionException {
    public PriorPriceSuggestionException() {
        super(PRIOR_PRICE_SUGGESTION.getErrorCode(),
                PRIOR_PRICE_SUGGESTION.getHttpStatus(),
                PRIOR_PRICE_SUGGESTION.getMessage()
        );
    }
}