package eightseconds.domain.pricesuggestion.dto;

import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "입찰하기 위한 응답 객체")
public class PriceSuggestionResponse {

    private Long itemId;
    private Long userId;
    private String picture;
    private Long priceSuggestionId;
    private int suggestionPrice;
    private String username;
    private int maximumPrice;
    private int theNumberOfParticipants;
    private EItemSoldStatus soldStatus;

    public static PriceSuggestionResponse from(Item item, User user, PriceSuggestion priceSuggestion
            , int maximumPrice, int theNumberOfParticipants, EItemSoldStatus soldStatus) {
        return PriceSuggestionResponse.builder()
                .itemId(item.getId())
                .userId(user.getId())
                .picture(user.getPicture())
                .priceSuggestionId(priceSuggestion.getId())
                .suggestionPrice(priceSuggestion.getSuggestionPrice())
                .username(user.getUsername())
                .maximumPrice(maximumPrice)
                .theNumberOfParticipants(theNumberOfParticipants)
                .soldStatus(soldStatus)
                .build();
    }
}
