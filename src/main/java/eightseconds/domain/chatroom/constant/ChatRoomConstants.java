package eightseconds.domain.chatroom.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import eightseconds.domain.item.constant.ItemConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatRoomConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EChatRoomServiceImpl {
        eNotFoundChatRoomExceptionMessage("해당 id에 해당하는 채팅방이 없습니다."),
        eNotFoundUserChatRoomExceptionMessage("유저 id와 채팅방id에 해당하는 userChatRoom이 없습니다."),
        eAlreadyEnterExceptionMessage("이미 채팅방에 입장했습니다.");

        private String value;

    }
}
