package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_MATCH_REFRESH_TOKEN;

public class NotValidRefreshTokenException extends UserException {
    public NotValidRefreshTokenException() {
        super(NOT_MATCH_REFRESH_TOKEN.getErrorCode(),
                NOT_MATCH_REFRESH_TOKEN.getHttpStatus(),
                NOT_MATCH_REFRESH_TOKEN.getMessage()
        );
    }
}
