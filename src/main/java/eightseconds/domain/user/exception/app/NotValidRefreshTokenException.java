package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotValidRefreshTokenException extends UserException {
    public NotValidRefreshTokenException() {
        super(UserExceptionList.NOT_MATCH_REFRESH_TOKEN.getCode(),
                UserExceptionList.NOT_MATCH_REFRESH_TOKEN.getHttpStatus(),
                UserExceptionList.NOT_MATCH_REFRESH_TOKEN.getMessage()
        );
    }
}
