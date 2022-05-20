package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_FOUND_USER;

public class NotFoundUserException extends UserException {
    public NotFoundUserException() {
        super(NOT_FOUND_USER.getErrorCode(),
                NOT_FOUND_USER.getHttpStatus(),
                NOT_FOUND_USER.getMessage()
        );
    }
}