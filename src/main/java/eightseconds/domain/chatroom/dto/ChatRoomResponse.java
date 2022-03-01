package eightseconds.domain.chatroom.dto;

import eightseconds.domain.chatmessage.entity.ChatMessage;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.entity.Item;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "유저의 채팅방을 조회하기 위한 응답 객체")
public class ChatRoomResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long itemId;
    private List<String> fileNames;
    private String latestMessage;
    private LocalDateTime latestTime;

    public static ChatRoomResponse from(UserChatRoom userChatRoom) {
        List<String> fileNames = new ArrayList<>();
        Item item = userChatRoom.getChatRoom().getItem();
        if (userChatRoom.getChatRoom().getItem().getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = item.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        List<ChatMessage> chatMessages = userChatRoom.getChatRoom().getChatMessages();
        LocalDateTime tempLatestTime = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        String tempLatestMessage = null;
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.getCreatedDate().isAfter(tempLatestTime)) {
                tempLatestTime = chatMessage.getCreatedDate().withNano(0);
                tempLatestMessage = chatMessage.getMessage();}}
        System.out.println("AfterLocalDateTime"+tempLatestTime);
        return ChatRoomResponse.builder()
                .id(userChatRoom.getChatRoom().getId())
                .userId(userChatRoom.getUser().getId())
                .userName(userChatRoom.getUser().getUsername())
                .itemId(item.getId())
                .fileNames(fileNames)
                .latestMessage(tempLatestMessage)
                .latestTime(tempLatestTime)
                .build();
    }
}
