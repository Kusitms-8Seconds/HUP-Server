package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 응답 객체")
public class LoginResponse {

    private String token;
    private Long userId;

    public static LoginResponse from(String token, Long userId) {
        return LoginResponse.builder()
                .token(token)
                .userId(userId)
                .build();
    }

}
