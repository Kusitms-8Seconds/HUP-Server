package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class AlreadyRegisteredEmailException extends UserException {
    public AlreadyRegisteredEmailException() {
        super(UserExceptionList.ALREADY_REGISTERED_EMAIL.getCode(),
                UserExceptionList.ALREADY_REGISTERED_EMAIL.getHttpStatus(),
                UserExceptionList.ALREADY_REGISTERED_EMAIL.getMessage()
        );
    }
}
