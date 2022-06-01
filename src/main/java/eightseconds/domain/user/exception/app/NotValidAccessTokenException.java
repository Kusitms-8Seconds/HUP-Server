package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_VALID_ACCESS_TOKEN;

public class NotValidAccessTokenException extends UserException {
    public NotValidAccessTokenException() {
        super(NOT_VALID_ACCESS_TOKEN.getErrorCode(),
                NOT_VALID_ACCESS_TOKEN.getHttpStatus(),
                NOT_VALID_ACCESS_TOKEN.getMessage()
        );
    }
}
