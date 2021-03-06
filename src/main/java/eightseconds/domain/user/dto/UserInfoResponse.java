package eightseconds.domain.user.dto;

import eightseconds.domain.user.constant.UserConstants.*;
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
@ApiModel(description = "유저 정보 응답 객체")
public class UserInfoResponse {

    private Long userId;
    private String loginId;
    private String email;
    private String username;
    private String phoneNumber;
    private String picture;
    private boolean activated;
    private boolean emailAuthActivated;
    private ELoginType loginType;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .picture(user.getPicture())
                .activated(user.isActivated())
                .emailAuthActivated(user.isEmailAuthActivated())
                .loginType(user.getLoginType())
                .build();
    }
}
