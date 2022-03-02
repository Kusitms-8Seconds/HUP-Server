package eightseconds.domain.chatroom.respoistory;

import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    List<UserChatRoom> findAllByUser(User user);
    List<UserChatRoom> findAllByChatRoomId(Long chatRoomId);
    Optional<UserChatRoom> findOneByUserIdAndChatRoomId(Long userId, Long chatRoomId);

}
