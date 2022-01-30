package eightseconds.domain.item.dto;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "상품 낙찰을 위한 응답 객체")
public class SoldResponse {
    Long itemId;
    Long priceSuggestionId;
    int salesPrice;

    public static SoldResponse from(Item item, PriceSuggestion priceSuggestion) {
        return SoldResponse.builder()
                .itemId(item.getId())
                .priceSuggestionId(priceSuggestion.getId())
                .salesPrice(item.getSoldPrice())
                .build();
    }
}
