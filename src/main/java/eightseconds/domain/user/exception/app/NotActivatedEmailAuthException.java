package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_ACTIVATED_EMAIL_AUTH;

public class NotActivatedEmailAuthException extends UserException {
    public NotActivatedEmailAuthException() {
        super(NOT_ACTIVATED_EMAIL_AUTH.getErrorCode(),
                NOT_ACTIVATED_EMAIL_AUTH.getHttpStatus(),
                NOT_ACTIVATED_EMAIL_AUTH.getMessage()
        );
    }
}
