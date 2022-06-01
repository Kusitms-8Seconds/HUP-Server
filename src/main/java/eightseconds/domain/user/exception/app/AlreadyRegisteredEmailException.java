package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.ALREADY_REGISTERED_EMAIL;

public class AlreadyRegisteredEmailException extends UserException {
    public AlreadyRegisteredEmailException() {
        super(ALREADY_REGISTERED_EMAIL.getErrorCode(),
                ALREADY_REGISTERED_EMAIL.getHttpStatus(),
                ALREADY_REGISTERED_EMAIL.getMessage()
        );
    }
}
