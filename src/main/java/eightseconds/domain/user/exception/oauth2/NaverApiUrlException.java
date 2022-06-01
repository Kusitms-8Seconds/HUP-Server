package eightseconds.domain.user.exception.oauth2;

import static eightseconds.domain.user.constant.UserConstants.OAuth2UserExceptionList.NAVER_API_URI;

public class NaverApiUrlException extends OAuth2UserException{
    public NaverApiUrlException() {
        super(NAVER_API_URI.getErrorCode(),
                NAVER_API_URI.getHttpStatus(),
                NAVER_API_URI.getMessage()
        );
    }
}
