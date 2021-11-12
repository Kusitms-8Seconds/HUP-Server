package eightseconds.domain.pricesuggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceSuggestionRequest {

    private Long itemId;
    private Long userId;
    private int suggestionPrice;

}
