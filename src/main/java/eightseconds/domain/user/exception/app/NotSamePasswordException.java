package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants;

public class NotSamePasswordException extends IllegalArgumentException {
    public NotSamePasswordException() {
        super(UserConstants.EUserServiceImpl.eNotSamePasswordExceptionMessage.getValue());
    }
}
