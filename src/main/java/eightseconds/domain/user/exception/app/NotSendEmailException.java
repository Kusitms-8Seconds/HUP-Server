package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_SEND_EMAIL;

public class NotSendEmailException extends UserException {
    public NotSendEmailException() {
        super(NOT_SEND_EMAIL.getErrorCode(),
                NOT_SEND_EMAIL.getHttpStatus(),
                NOT_SEND_EMAIL.getMessage()
        );
    }
}
