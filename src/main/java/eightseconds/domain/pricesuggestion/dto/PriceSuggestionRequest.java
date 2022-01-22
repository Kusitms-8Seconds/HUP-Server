package eightseconds.domain.pricesuggestion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "입찰하기 위한 요청 객체")
public class PriceSuggestionRequest {

    @NotNull(message = "상품의 itemId를 입력해주세요.")
    @ApiModelProperty(notes = "itemId를 입력해 주세요.")
    private Long itemId;

    @NotNull(message = "회원의 userId를 입력해주세요.")
    @ApiModelProperty(notes = "userId를 입력해 주세요.")
    private Long userId;

    @NotNull(message = "입찰가격을 입력해주세요.")
    @ApiModelProperty(notes = "입찰하고자 하는 가격을 입력해주세요.")
    private int suggestionPrice;

}
