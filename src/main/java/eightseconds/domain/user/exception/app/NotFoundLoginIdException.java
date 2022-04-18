package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;

public class NotFoundLoginIdException extends IllegalArgumentException {
    public NotFoundLoginIdException() {
        super(EUserServiceImpl.eNotFoundLoginIdExceptionMessage.getValue());
    }
}
