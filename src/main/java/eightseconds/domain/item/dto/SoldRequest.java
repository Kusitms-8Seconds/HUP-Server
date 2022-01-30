package eightseconds.domain.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "상품 낙찰을 위한 요청 객체")
public class SoldRequest {

    @NotBlank(message = "낙찰하고자 하는 상품의 아이디를 입력해주세요.")
    @ApiModelProperty(notes = "상품 아이디를 입력해 주세요.")
    Long itemId;
}
