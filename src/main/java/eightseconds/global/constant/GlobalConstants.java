package eightseconds.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GlobalConstants {

    @Getter
    public enum ExceptionCode {
        UNKNOWN_ERROR("알려지지 않은 에러입니다.", "UNKNOWN_ERROR"),
        WRONG_TYPE_TOKEN("잘못된 JWT 서명입니다.", "WRONG_TYPE_TOKEN"),
        EXPIRED_TOKEN("만료된 토큰입니다.", "EXPIRED_TOKEN"),
        UNSUPPORTED_TOKEN("지원되지 않는 토큰입니다.", "UNSUPPORTED_TOKEN"),
        ACCESS_DENIED("접근이 거부되었습니다.", "ACCESS_DENIED"),
        WRONG_TOKEN("JWT 토큰이 잘못되었습니다.","WRONG_TOKEN");

        private String message;
        private String code;

        ExceptionCode(String message, String code) {
            this.message = message;
            this.code = code;
        }
    }
}
