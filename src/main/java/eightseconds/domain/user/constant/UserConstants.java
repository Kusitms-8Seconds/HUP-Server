package eightseconds.domain.user.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import eightseconds.domain.item.constant.ItemConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserConstants {

    SUCCESS_SIGN_UP("회원가입을 완료했습니다.");

    private final String message;

    @Getter
    @AllArgsConstructor
    public enum ELoginType{
        eGoogle("구글"),
        eNaver("네이버"),
        eKakao("카카오"),
        eApp("앱");

        private final String name;

        @JsonCreator
        public static ELoginType from(String s) {
            return ELoginType.valueOf(s);
        }
    }
}
