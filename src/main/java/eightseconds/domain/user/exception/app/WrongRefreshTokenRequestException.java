package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class WrongRefreshTokenRequestException extends UserException {
    public WrongRefreshTokenRequestException() {
        super(UserExceptionList.WRONG_REFRESH_TOKEN_REQUEST.getCode(),
                UserExceptionList.WRONG_REFRESH_TOKEN_REQUEST.getHttpStatus(),
                UserExceptionList.WRONG_REFRESH_TOKEN_REQUEST.getMessage()
        );
    }
}
