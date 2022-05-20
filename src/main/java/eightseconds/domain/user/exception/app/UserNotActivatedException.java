package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.USER_NOT_ACTIVATED;

public class UserNotActivatedException extends UserException {
    public UserNotActivatedException() {
        super(USER_NOT_ACTIVATED.getErrorCode(),
                USER_NOT_ACTIVATED.getHttpStatus(),
                USER_NOT_ACTIVATED.getMessage()
        );
    }
}
