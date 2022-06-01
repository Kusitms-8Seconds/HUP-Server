package eightseconds.domain.chatroom.exception;

import static eightseconds.domain.chatroom.constant.ChatRoomConstants.ChatRoomExceptionList.NOT_FOUND_CHAT_ROOM;

public class NotFoundChatRoomException extends ChatRoomException {
    public NotFoundChatRoomException() {
        super(NOT_FOUND_CHAT_ROOM.getErrorCode(),
                NOT_FOUND_CHAT_ROOM.getHttpStatus(),
                NOT_FOUND_CHAT_ROOM.getMessage()
        );
    }
}
