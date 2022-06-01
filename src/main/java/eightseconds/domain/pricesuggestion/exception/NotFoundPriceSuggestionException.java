package eightseconds.domain.pricesuggestion.exception;

import eightseconds.domain.scrap.exception.ScrapException;
import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.NOT_FOUND_PRICE_SUGGESTION;

public class NotFoundPriceSuggestionException extends PriceSuggestionException {
    public NotFoundPriceSuggestionException() {
        super(NOT_FOUND_PRICE_SUGGESTION.getErrorCode(),
                NOT_FOUND_PRICE_SUGGESTION.getHttpStatus(),
                NOT_FOUND_PRICE_SUGGESTION.getMessage()
        );
    }
}
