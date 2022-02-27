package eightseconds.domain.chatmessage.respoistory;

import eightseconds.domain.chatmessage.entity.ChatMessage;
import eightseconds.domain.chatroom.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByChatRoom(Pageable pageable, ChatRoom chatRoom);
}
