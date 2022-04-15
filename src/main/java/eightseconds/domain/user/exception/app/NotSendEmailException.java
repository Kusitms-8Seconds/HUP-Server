package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;

public class NotSendEmailException extends RuntimeException {
    public NotSendEmailException() {
        super(EUserServiceImpl.eNotSendEmailExceptionMessage.getValue());
    }
}
