package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotFoundUserException extends UserException {
    public NotFoundUserException() {
        super(UserExceptionList.NOT_FOUND_USER.getCode(),
                UserExceptionList.NOT_FOUND_USER.getHttpStatus(),
                UserExceptionList.NOT_FOUND_USER.getMessage()
        );
    }
}