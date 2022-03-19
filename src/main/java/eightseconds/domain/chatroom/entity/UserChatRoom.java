package eightseconds.domain.chatroom.entity;

import eightseconds.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserChatRoom {

    @Id
    @GeneratedValue
    @Column(name = "user_chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private boolean entryCheck;

    /**
     * 연관관계 메서드
     */

    public void setUser(User user) {
        this.user = user;
        user.getUserChatRooms().add(this);
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        chatRoom.getUserChatRooms().add(this);
    }

}
