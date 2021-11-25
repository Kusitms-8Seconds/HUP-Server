//package eightseconds.domain.chatmessage.entity;
//
//import eightseconds.domain.chatroom.entity.ChatRoom;
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
//public class ChatMessage extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "chat_message_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "chat_room_id")
//    private ChatRoom chatRoom;
//
//    private String message;
//    private boolean read_or_not;
//}
