package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotAuthenticationForChangePasswordException extends UserException {
    public NotAuthenticationForChangePasswordException() {
        super(UserExceptionList.NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD.getCode(),
                UserExceptionList.NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD.getHttpStatus(),
                UserExceptionList.NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD.getMessage()
        );
    }
}
