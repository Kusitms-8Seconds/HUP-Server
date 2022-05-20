package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.ALREADY_REGISTERED_USER;

public class AlreadyRegisteredUserException extends UserException {
    public AlreadyRegisteredUserException() {
        super(ALREADY_REGISTERED_USER.getErrorCode(),
                ALREADY_REGISTERED_USER.getHttpStatus(),
                ALREADY_REGISTERED_USER.getMessage()
        );
    }
}
