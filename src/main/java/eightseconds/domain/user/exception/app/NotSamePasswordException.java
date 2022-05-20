package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotSamePasswordException extends UserException {
    public NotSamePasswordException() {
        super(UserExceptionList.NOT_SAME_PASSWORD.getCode(),
                UserExceptionList.NOT_SAME_PASSWORD.getHttpStatus(),
                UserExceptionList.NOT_SAME_PASSWORD.getMessage()
        );
    }
}
