package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotFoundLoginIdException extends UserException {
    public NotFoundLoginIdException() {
        super(UserExceptionList.NOT_FOUND_LOGIN_ID.getCode(),
                UserExceptionList.NOT_FOUND_LOGIN_ID.getHttpStatus(),
                UserExceptionList.NOT_FOUND_LOGIN_ID.getMessage()
        );
    }
}
