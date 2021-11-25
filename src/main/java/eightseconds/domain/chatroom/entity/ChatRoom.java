//package eightseconds.domain.chatroom.entity;
//
//import eightseconds.domain.chatmessage.entity.ChatMessage;
//import eightseconds.domain.item.entity.Item;
//import eightseconds.domain.user.entity.User;
//import eightseconds.global.entity.BaseTimeEntity;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ChatRoom extends BaseTimeEntity {
//
//    @Getter(AccessLevel.NONE)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "chat_room_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToMany(mappedBy = "chatRoom")
//    private List<ChatMessage> chatMessages = new ArrayList<>();
//}
