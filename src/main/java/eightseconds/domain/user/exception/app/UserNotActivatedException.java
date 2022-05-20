package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class UserNotActivatedException extends UserException {
    public UserNotActivatedException() {
        super(UserExceptionList.USER_NOT_ACTIVATED.getCode(),
                UserExceptionList.USER_NOT_ACTIVATED.getHttpStatus(),
                UserExceptionList.USER_NOT_ACTIVATED.getMessage()
        );
    }
}
