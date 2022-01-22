package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "카카오 로그인을 위한 요청 객체")
public class OAuth2KakaoLoginRequest {

    @NotBlank(message = "카카오의 accessToken을 입력해 주세요.")
    @ApiModelProperty(notes = "카카오의 accessToken을 입력해 주세요.")
    private String accessToken;
}
