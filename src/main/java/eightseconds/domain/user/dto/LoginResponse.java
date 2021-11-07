package eightseconds.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
