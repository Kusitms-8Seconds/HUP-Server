package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotSendEmailException extends UserException {
    public NotSendEmailException() {
        super(UserExceptionList.NOT_SEND_EMAIL.getCode(),
                UserExceptionList.NOT_SEND_EMAIL.getHttpStatus(),
                UserExceptionList.NOT_SEND_EMAIL.getMessage()
        );
    }
}
