package eightseconds.domain.chatroom.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ChatRoomConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EChatRoomServiceImpl {
        eOutUserChatRoomMessage("님이 채팅방을 퇴장했습니다.");
        private String value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ChatRoomExceptionList {
        NOT_FOUND_CHAT_ROOM("CR0001", HttpStatus.NOT_FOUND, "해당 id에 해당하는 채팅방이 없습니다."),
        NOT_FOUND_USER_CHAT_ROOM("CR0002", HttpStatus.NOT_FOUND, "해당 유저의 채팅방이 없습니다."),
        ALREADY_ENTER("CR0003", HttpStatus.CONFLICT, "이미 채팅방에 입장했습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
