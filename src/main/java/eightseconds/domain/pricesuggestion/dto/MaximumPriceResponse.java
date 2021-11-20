package eightseconds.domain.pricesuggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MaximumPriceResponse {

    private int maximumPrice;

    public static MaximumPriceResponse from(int maximumPrice) {
        return MaximumPriceResponse.builder()
                .maximumPrice(maximumPrice)
                .build();
    }
}
