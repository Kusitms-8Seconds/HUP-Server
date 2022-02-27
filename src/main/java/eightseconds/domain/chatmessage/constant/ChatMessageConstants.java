package eightseconds.domain.chatmessage.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import eightseconds.domain.item.constant.ItemConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatMessageConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EChatMessageSTOMPController {
        eChatRoomEnterMessage("님이 채팅방에 참여하였습니다.");

        private String value;

        @JsonCreator
        public static ItemConstants.EItemCategory from(String s) {
            return ItemConstants.EItemCategory.valueOf(s);
        }
    }
}
