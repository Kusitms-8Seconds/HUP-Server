package eightseconds.domain.pricesuggestion.dto;

import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PriceSuggestionListResponse {

    private Long priceSuggestionId;
    private Long userId;
    private Long itemId;
    private int suggestionPrice;
    private boolean acceptState;

    public static PriceSuggestionListResponse from(PriceSuggestion priceSuggestion) {
        return PriceSuggestionListResponse.builder()
                .priceSuggestionId(priceSuggestion.getId())
                .userId(priceSuggestion.getUser().getId())
                .itemId(priceSuggestion.getItem().getId())
                .suggestionPrice(priceSuggestion.getSuggestionPrice())
                .acceptState(priceSuggestion.isAcceptState())
                .build();
    }
}
