package eightseconds.domain.pricesuggestion.exception;

import eightseconds.domain.scrap.exception.ScrapException;

import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.ALREADY_SOLD_OUT;

public class AlreadySoldOutException extends PriceSuggestionException {
    public AlreadySoldOutException() {
        super(ALREADY_SOLD_OUT.getErrorCode(),
                ALREADY_SOLD_OUT.getHttpStatus(),
                ALREADY_SOLD_OUT.getMessage()
        );
    }
}