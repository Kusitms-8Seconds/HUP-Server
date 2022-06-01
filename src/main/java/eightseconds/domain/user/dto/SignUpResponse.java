package eightseconds.domain.user.dto;

import eightseconds.domain.user.entity.User;
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

    public static SignUpResponse from(Long userId, String loginId) {
        return SignUpResponse.builder()
                .userId(userId)
                .loginId(loginId)
                .build();
    }

    public static SignUpResponse from(User user) {
        return SignUpResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .build();
    }
}
