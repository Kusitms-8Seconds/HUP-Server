package eightseconds.domain.chatroom.entity;

import eightseconds.domain.chatmessage.entity.ChatMessage;
import eightseconds.domain.item.entity.Item;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public static ChatRoom toEntity() {
        return ChatRoom.builder()
                .build();
    }
}
