package eightseconds.domain.chatroom.dto;

import eightseconds.domain.chatroom.entity.UserChatRoom;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "유저의 채팅방을 조회하기 위한 응답 객체")
public class ChatRoomResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long itemId;
    private String itemName;

    public static ChatRoomResponse from(UserChatRoom userChatRoom) {
        return ChatRoomResponse.builder()
                .id(userChatRoom.getChatRoom().getId())
                .userId(userChatRoom.getUser().getId())
                .userName(userChatRoom.getUser().getUsername())
                .itemName(userChatRoom.getChatRoom().getItem().getItemName())
                .build();
    }
}
