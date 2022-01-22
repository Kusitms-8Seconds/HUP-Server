package eightseconds.domain.pricesuggestion.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 상품의 최고입찰 가격을 조회하기 위한 응답 객체")
public class MaximumPriceResponse {

    private int maximumPrice;

    public static MaximumPriceResponse from(int maximumPrice) {
        return MaximumPriceResponse.builder()
                .maximumPrice(maximumPrice)
                .build();
    }
}
