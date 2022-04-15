package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;

public class NotAuthenticationForPasswordException extends RuntimeException{
    public NotAuthenticationForPasswordException() {
        super(EUserServiceImpl.eNotAuthenticationPasswordExceptionMessage.getValue());
    }
}
