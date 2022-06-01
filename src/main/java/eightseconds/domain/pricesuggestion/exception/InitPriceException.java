package eightseconds.domain.pricesuggestion.exception;

import eightseconds.domain.scrap.exception.ScrapException;

import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.INIT_PRICE;

public class InitPriceException extends PriceSuggestionException {
    public InitPriceException() {
        super(INIT_PRICE.getErrorCode(),
                INIT_PRICE.getHttpStatus(),
                INIT_PRICE.getMessage()
        );
    }
}
