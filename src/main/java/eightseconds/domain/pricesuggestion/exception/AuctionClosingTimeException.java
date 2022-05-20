package eightseconds.domain.pricesuggestion.exception;

import eightseconds.domain.scrap.exception.ScrapException;

import static eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.PriceSuggestionExceptionList.AUCTION_CLOSING_TIME;

public class AuctionClosingTimeException extends PriceSuggestionException {
    public AuctionClosingTimeException() {
        super(AUCTION_CLOSING_TIME.getErrorCode(),
                AUCTION_CLOSING_TIME.getHttpStatus(),
                AUCTION_CLOSING_TIME.getMessage()
        );
    }
}
