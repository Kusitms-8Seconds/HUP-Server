package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 요청 객체")
public class LoginRequest {

    @NotBlank(message = "회원의 로그인Id를 입력해주세요.")
    @Size(min = 5, max = 11, message = "로그인 Id는 크기가 5에서 11사이여야 합니다.")
    @ApiModelProperty(notes = "로그인 Id를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    //@Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;

    @ApiModelProperty(notes = "알림을 받기 위한 회원의 타겟토큰을 입력해 주세요.")
    private String targetToken;
}
