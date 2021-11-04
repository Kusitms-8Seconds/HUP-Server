package kusitms.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponse {

    private String loginId;
    private String message;

    public static SignUpResponse from(String loginId, String message) {
        return SignUpResponse.builder()
                .loginId(loginId)
                .message(message)
                .build();
    }
}
