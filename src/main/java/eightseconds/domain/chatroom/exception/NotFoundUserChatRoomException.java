package eightseconds.domain.chatroom.exception;

import static eightseconds.domain.chatroom.constant.ChatRoomConstants.ChatRoomExceptionList.NOT_FOUND_USER_CHAT_ROOM;

public class NotFoundUserChatRoomException extends ChatRoomException {
    public NotFoundUserChatRoomException() {
        super(NOT_FOUND_USER_CHAT_ROOM.getErrorCode(),
                NOT_FOUND_USER_CHAT_ROOM.getHttpStatus(),
                NOT_FOUND_USER_CHAT_ROOM.getMessage()
        );
    }
}
