package eightseconds.domain.user.exception.oauth2;

import static eightseconds.domain.user.constant.UserConstants.OAuth2UserExceptionList.GOOGLE_INVALID_ID_TOKEN;

public class GoogleInvalidIdTokenException extends OAuth2UserException{
    public GoogleInvalidIdTokenException() {
        super(GOOGLE_INVALID_ID_TOKEN.getErrorCode(),
                GOOGLE_INVALID_ID_TOKEN.getHttpStatus(),
                GOOGLE_INVALID_ID_TOKEN.getMessage()
        );
    }
}
