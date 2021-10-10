package kusitms.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserConstants {

    SUCCESS_SIGN_UP("회원가입을 완료했습니다.");

    private final String message;
}
