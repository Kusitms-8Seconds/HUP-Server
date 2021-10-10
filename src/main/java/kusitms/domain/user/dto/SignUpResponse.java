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

    private String email;
    private String message;

    public static SignUpResponse of(String email, String message) {
        return SignUpResponse.builder()
                .email(email)
                .message(message)
                .build();
    }
}
