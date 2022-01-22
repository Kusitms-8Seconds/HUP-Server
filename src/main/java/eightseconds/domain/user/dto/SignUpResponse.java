package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "회원가입을 위한 응답 객체")
public class SignUpResponse {

    private Long userId;
    private String loginId;
    private String message;

    public static SignUpResponse from(Long userId, String loginId, String message) {
        return SignUpResponse.builder()
                .userId(userId)
                .loginId(loginId)
                .message(message)
                .build();
    }
}
