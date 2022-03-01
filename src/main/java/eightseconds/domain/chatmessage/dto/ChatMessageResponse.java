package eightseconds.domain.chatmessage.dto;

import eightseconds.domain.chatmessage.entity.ChatMessage;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "채팅방의 메세지들을 조회하기 위한 응답 객체")
public class ChatMessageResponse {

    private Long id;
    private Long userId;
    private String userName;
    private String message;
    private LocalDateTime createdDate;

    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .userId(chatMessage.getUser().getId())
                .userName(chatMessage.getUser().getUsername())
                .message(chatMessage.getMessage())
                .createdDate(chatMessage.getCreatedDate())
                .build();
    }
}
