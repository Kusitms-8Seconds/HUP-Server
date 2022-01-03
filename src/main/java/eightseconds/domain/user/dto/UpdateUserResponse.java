package eightseconds.domain.user.dto;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserResponse {

    private Long userId;
    private String loginId;
    private String email;
    private String username;
    private String phoneNumber;
    private String picture;
    private boolean activated;
    private UserConstants.ELoginType loginType;

    public static UpdateUserResponse from(User user) {
        return UpdateUserResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .picture(user.getPicture())
                .activated(user.isActivated())
                .loginType(user.getLoginType())
                .build();
    }
}
