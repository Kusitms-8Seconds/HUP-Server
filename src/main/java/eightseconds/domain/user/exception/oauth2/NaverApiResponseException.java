package eightseconds.domain.user.exception.oauth2;

import static eightseconds.domain.user.constant.UserConstants.OAuth2UserExceptionList.NAVER_API_RESPONSE;

public class NaverApiResponseException extends OAuth2UserException{
    public NaverApiResponseException() {
        super(NAVER_API_RESPONSE.getErrorCode(),
                NAVER_API_RESPONSE.getHttpStatus(),
                NAVER_API_RESPONSE.getMessage()
        );
    }
}
