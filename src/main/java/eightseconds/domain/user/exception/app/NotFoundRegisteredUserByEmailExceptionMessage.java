package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

import static eightseconds.domain.user.constant.UserConstants.UserExceptionList.NOT_FOUND_REGISTERED_USER_BY_EMAIL;

public class NotFoundRegisteredUserByEmailExceptionMessage extends UserException{
    public NotFoundRegisteredUserByEmailExceptionMessage() {
        super(NOT_FOUND_REGISTERED_USER_BY_EMAIL.getErrorCode(),
                NOT_FOUND_REGISTERED_USER_BY_EMAIL.getHttpStatus(),
                NOT_FOUND_REGISTERED_USER_BY_EMAIL.getMessage()
        );
    }
}
