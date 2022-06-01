package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD;

public class NotAuthenticationForChangePasswordException extends UserException {
    public NotAuthenticationForChangePasswordException() {
        super(NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD.getErrorCode(),
                NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD.getHttpStatus(),
                NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD.getMessage()
        );
    }
}
