package eightseconds.domain.user.exception.app;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.WRONG_REFRESH_TOKEN_REQUEST;

public class WrongRefreshTokenRequestException extends UserException {
    public WrongRefreshTokenRequestException() {
        super(WRONG_REFRESH_TOKEN_REQUEST.getErrorCode(),
                WRONG_REFRESH_TOKEN_REQUEST.getHttpStatus(),
                WRONG_REFRESH_TOKEN_REQUEST.getMessage()
        );
    }
}
