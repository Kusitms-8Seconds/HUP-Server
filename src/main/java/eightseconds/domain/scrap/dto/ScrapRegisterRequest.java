package eightseconds.domain.scrap.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "유저가 해당 상품을 스크랩하는 요청 객체")
public class ScrapRegisterRequest {

    @NotNull(message = "회원의 userId를 입력해주세요.")
    @ApiModelProperty(notes = "userId를 입력해 주세요.")
    private Long userId;

    @NotNull(message = "상품의 itemId를 입력해주세요.")
    @ApiModelProperty(notes = "itemId를 입력해 주세요.")
    private Long itemId;
}
