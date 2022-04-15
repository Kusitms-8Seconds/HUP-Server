package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;

public class NotFoundRegisteredEmailException extends IllegalArgumentException{
    public NotFoundRegisteredEmailException() {
        super(EUserServiceImpl.eNotFoundRegisteredUserExceptionMessage.getValue());
    }
}
