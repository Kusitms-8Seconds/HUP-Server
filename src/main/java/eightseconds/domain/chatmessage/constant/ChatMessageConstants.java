package eightseconds.domain.chatmessage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ChatMessageConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EChatMessageServiceImpl {
        eChatRoomEnterMessage("님이 채팅방에 참여하였습니다.");

        private String value;

    }

}
