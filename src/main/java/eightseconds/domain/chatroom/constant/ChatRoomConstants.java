package eightseconds.domain.chatroom.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatRoomConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EChatRoomServiceImpl {
        eNotFoundChatRoomExceptionMessage("해당 id에 해당하는 채팅방이 없습니다."),
        eNotFoundUserChatRoomExceptionMessage("해당 유저의 채팅방이 없습니다."),
        eAlreadyEnterExceptionMessage("이미 채팅방에 입장했습니다."),
        eOutUserChatRoomMessage("채팅방 나가기를 완료했습니다.");
        private String value;
    }
}
