package eightseconds.domain.pricesuggestion.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 상품의 최고입찰유저를 조회하기 위한 응답 객체")
public class BidderResponse {

    private String bidderName;

    public static BidderResponse from(String bidderName) {
        return BidderResponse.builder()
                .bidderName(bidderName)
                .build();
    }
}
