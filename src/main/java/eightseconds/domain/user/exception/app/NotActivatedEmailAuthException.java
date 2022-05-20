package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotActivatedEmailAuthException extends UserException {
    public NotActivatedEmailAuthException() {
        super(UserExceptionList.NOT_ACTIVATED_EMAIL_AUTH.getCode(),
                UserExceptionList.NOT_ACTIVATED_EMAIL_AUTH.getHttpStatus(),
                UserExceptionList.NOT_ACTIVATED_EMAIL_AUTH.getMessage()
        );
    }
}
