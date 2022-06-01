package eightseconds.domain.user.exception.oauth2;

import static eightseconds.domain.user.constant.UserConstants.OAuth2UserExceptionList.NAVER_NOT_FOUND;

public class NaverNotFoundException extends OAuth2UserException{
    public NaverNotFoundException() {
        super(NAVER_NOT_FOUND.getErrorCode(),
                NAVER_NOT_FOUND.getHttpStatus(),
                NAVER_NOT_FOUND.getMessage()
        );
    }
}
