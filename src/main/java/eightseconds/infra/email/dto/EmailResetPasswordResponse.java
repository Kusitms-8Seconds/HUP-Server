package eightseconds.infra.email.dto;

import eightseconds.domain.user.entity.User;
import eightseconds.global.dto.DefaultResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "비밀번호 재설정을 위한 이메일 응답 객체")
public class EmailResetPasswordResponse extends DefaultResponse {
    private Long userId;
    private String loginId;

    public static EmailResetPasswordResponse from(User user, String message) {
        EmailResetPasswordResponse emailResetPasswordResponse = EmailResetPasswordResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .build();
        emailResetPasswordResponse.setMessage(message);
        return emailResetPasswordResponse;
    }
}
