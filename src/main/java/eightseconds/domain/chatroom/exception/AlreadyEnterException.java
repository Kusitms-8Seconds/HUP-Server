package eightseconds.domain.chatroom.exception;

import eightseconds.domain.category.exeception.CategoryException;

import static eightseconds.domain.chatroom.constant.ChatRoomConstants.ChatRoomExceptionList.ALREADY_ENTER;

public class AlreadyEnterException extends ChatRoomException {
    public AlreadyEnterException() {
        super(ALREADY_ENTER.getErrorCode(),
                ALREADY_ENTER.getHttpStatus(),
                ALREADY_ENTER.getMessage()
        );
    }
}
