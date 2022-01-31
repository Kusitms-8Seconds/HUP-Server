package eightseconds.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ReissueRequest {
    @NotEmpty(message = "accessToken 을 입력해주세요.")
    private String accessToken;

    @NotEmpty(message = "refreshToken 을 입력해주세요.")
    private String refreshToken;
}
