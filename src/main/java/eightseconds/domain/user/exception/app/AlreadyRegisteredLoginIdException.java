package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.ALREADY_REGISTERED_LOGIN_ID;

public class AlreadyRegisteredLoginIdException extends UserException {
    public AlreadyRegisteredLoginIdException() {
        super(ALREADY_REGISTERED_LOGIN_ID.getErrorCode(),
                ALREADY_REGISTERED_LOGIN_ID.getHttpStatus(),
                ALREADY_REGISTERED_LOGIN_ID.getMessage()
        );
    }
}