package kusitms.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String email;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
