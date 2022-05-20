package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotValidAccessTokenException extends UserException {
    public NotValidAccessTokenException() {
        super(UserExceptionList.NOT_VALID_ACCESS_TOKEN.getCode(),
                UserExceptionList.NOT_VALID_ACCESS_TOKEN.getHttpStatus(),
                UserExceptionList.NOT_VALID_ACCESS_TOKEN.getMessage()
        );
    }
}
