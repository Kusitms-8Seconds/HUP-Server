package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class AlreadyRegisteredUserException extends UserException {
    public AlreadyRegisteredUserException() {
        super(UserExceptionList.ALREADY_REGISTERED_USER.getCode(),
                UserExceptionList.ALREADY_REGISTERED_USER.getHttpStatus(),
                UserExceptionList.ALREADY_REGISTERED_USER.getMessage()
        );
    }
}
