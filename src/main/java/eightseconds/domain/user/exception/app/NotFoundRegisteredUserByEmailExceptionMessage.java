package eightseconds.domain.user.exception.app;

import eightseconds.domain.user.constant.UserConstants.UserExceptionList;

public class NotFoundRegisteredUserByEmailExceptionMessage extends UserException{
    public NotFoundRegisteredUserByEmailExceptionMessage() {
        super(UserExceptionList.NOT_FOUND_REGISTERED_USER_BY_EMAIL.getCode(),
                UserExceptionList.NOT_FOUND_REGISTERED_USER_BY_EMAIL.getHttpStatus(),
                UserExceptionList.NOT_FOUND_REGISTERED_USER_BY_EMAIL.getMessage()
        );
    }
}
