package eightseconds.domain.item.dto;

import eightseconds.domain.item.constant.ItemConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원의 상품들을 조회하기 위한 요청 객체")
public class ItemOfUserRequest {

    @NotNull(message = "회원의 userId를 입력해주세요.")
    @ApiModelProperty(notes = "userId를 입력해 주세요.")
    private Long userId;

    @NotNull(message = "찾고자 하는 아이템들의 판매상태를 입력해주세요.")
    @ApiModelProperty(notes = "아이템의 판매상태를 입력해 주세요.")
    @Enumerated(EnumType.STRING)
    private ItemConstants.EItemSoldStatus soldStatus;

}
