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

    private Long userId;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse from(Long userId, TokenInfoResponse tokenInfoResponse) {
        return LoginResponse.builder()
                .userId(userId)
                .accessToken(tokenInfoResponse.getAccessToken())
                .refreshToken(tokenInfoResponse.getRefreshToken())
                .build();
    }

}
