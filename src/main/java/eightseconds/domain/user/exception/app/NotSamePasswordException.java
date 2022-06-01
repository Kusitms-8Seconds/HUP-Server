package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_SAME_PASSWORD;

public class NotSamePasswordException extends UserException {
    public NotSamePasswordException() {
        super(NOT_SAME_PASSWORD.getErrorCode(),
                NOT_SAME_PASSWORD.getHttpStatus(),
                NOT_SAME_PASSWORD.getMessage()
        );
    }
}
