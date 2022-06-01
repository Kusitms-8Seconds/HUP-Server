package eightseconds.domain.chatroom.exception;

import eightseconds.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class ChatRoomException extends ApplicationException {
    protected ChatRoomException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
