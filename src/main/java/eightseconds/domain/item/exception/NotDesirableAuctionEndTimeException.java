package eightseconds.domain.item.exception;

import static eightseconds.domain.item.constant.ItemConstants.ItemExceptionList.NOT_DESIRABLE_AUCTION_END_TIME;

public class NotDesirableAuctionEndTimeException extends ItemException {
    public NotDesirableAuctionEndTimeException() {
        super(NOT_DESIRABLE_AUCTION_END_TIME.getErrorCode(),
                NOT_DESIRABLE_AUCTION_END_TIME.getHttpStatus(),
                NOT_DESIRABLE_AUCTION_END_TIME.getMessage()
        );
    }
}