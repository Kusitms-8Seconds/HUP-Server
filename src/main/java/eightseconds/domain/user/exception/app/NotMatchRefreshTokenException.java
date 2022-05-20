package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotMatchRefreshTokenException extends UserException {
    public NotMatchRefreshTokenException() {
        super(UserExceptionList.NOT_MATCH_REFRESH_TOKEN.getCode(),
                UserExceptionList.NOT_MATCH_REFRESH_TOKEN.getHttpStatus(),
                UserExceptionList.NOT_MATCH_REFRESH_TOKEN.getMessage()
        );
    }
}
