package eightseconds.domain.chatmessage.entity;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String message;

    /**
     * 연관관계 메서드
     */

    public void setUser(User user) {
        this.user = user;
        user.getChatMessages().add(this);
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getChatMessages().add(this);
    }

    /**
     * 생성 메서드
     */

    public static ChatMessage toEntity(ChatMessageRequest chatMessageRequest) {
        return ChatMessage.builder()
                .message(chatMessageRequest.getMessage())
                .build();
    }
}
