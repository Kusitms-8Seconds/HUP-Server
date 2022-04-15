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
@ApiModel(description = "비밀번호 재설정을 위한 응답 객체")
public class ResetPasswordResponse {
    private Long userId;
    private String loginId;

    public static ResetPasswordResponse from(User user) {
        return ResetPasswordResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .build();
    }
}
