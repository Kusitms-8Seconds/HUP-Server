package eightseconds.domain.user.dto;

import eightseconds.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ApiModel(description = "회원가입을 위한 요청 객체")
public class SignUpRequest {

    @NotBlank(message = "회원의 회원가입Id를 입력해주세요.")
    @Size(min = 5, max = 11, message = "회원가입 Id는 크기가 5에서 11사이여야 합니다.")
    @ApiModelProperty(notes = "회원가입 Id를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "회원의 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    @ApiModelProperty(notes = "회원의 이메일을 입력해 주세요.")
    private String email;

    @NotBlank(message = "회원 이름을 입력해 주세요.")
    @Size(min = 2, max = 10, message = "이름은 2글자 이상 10글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원 이름을 입력해 주세요.")
    private String username;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @ApiModelProperty(notes = "회원의 전화번호를 입력해 주세요.")
    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
