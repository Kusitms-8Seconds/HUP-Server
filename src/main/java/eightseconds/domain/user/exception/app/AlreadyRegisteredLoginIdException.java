package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class AlreadyRegisteredLoginIdException extends UserException {
    public AlreadyRegisteredLoginIdException() {
        super(UserExceptionList.ALREADY_REGISTERED_LOGIN_ID.getCode(),
                UserExceptionList.ALREADY_REGISTERED_LOGIN_ID.getHttpStatus(),
                UserExceptionList.ALREADY_REGISTERED_LOGIN_ID.getMessage()
        );
    }
}