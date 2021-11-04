package eightseconds.domain.user.dto;

import eightseconds.domain.user.entity.User;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class SignUpRequest {

    @Size(min = 5, max = 11)
    private String loginId;

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 최소 2글자 이상 10글자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 8, max = 16, message = "패스워드는 8글자 이상 16글자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
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
