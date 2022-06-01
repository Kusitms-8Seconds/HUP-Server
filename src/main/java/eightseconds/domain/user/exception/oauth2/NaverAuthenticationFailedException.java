package eightseconds.domain.user.exception.oauth2;

import static eightseconds.domain.user.constant.UserConstants.OAuth2UserExceptionList.NAVER_AUTHENTICATION_FAILED;

public class NaverAuthenticationFailedException extends OAuth2UserException{
    public NaverAuthenticationFailedException() {
        super(NAVER_AUTHENTICATION_FAILED.getErrorCode(),
                NAVER_AUTHENTICATION_FAILED.getHttpStatus(),
                NAVER_AUTHENTICATION_FAILED.getMessage()
        );
    }
}
