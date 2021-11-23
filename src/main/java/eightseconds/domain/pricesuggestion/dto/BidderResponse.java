package eightseconds.domain.pricesuggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BidderResponse {

    private String bidderName;

    public static BidderResponse from(String bidderName) {
        return BidderResponse.builder()
                .bidderName(bidderName)
                .build();
    }
}
