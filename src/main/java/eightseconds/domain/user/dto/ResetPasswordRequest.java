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
@ApiModel(description = "비밀번호를 찾기 위한 요청 객체")
public class ResetPasswordRequest {

    @NotNull(message = "회원의 유저 Id를 입력해주세요.")
    private Long userId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "회원의 비밀번호를 다시 한 번 입력해 주세요.")
    @Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 비밀번호를 다시 한 번 입력해 주세요.")
    private String checkPassword;
}
