package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_FOUND_LOGIN_ID;

public class NotFoundLoginIdException extends UserException {
    public NotFoundLoginIdException() {
        super(NOT_FOUND_LOGIN_ID.getErrorCode(),
                NOT_FOUND_LOGIN_ID.getHttpStatus(),
                NOT_FOUND_LOGIN_ID.getMessage()
        );
    }
}
