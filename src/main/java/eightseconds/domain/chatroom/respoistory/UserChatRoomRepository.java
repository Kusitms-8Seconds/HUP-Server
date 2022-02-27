package eightseconds.domain.chatroom.respoistory;

import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    Page<UserChatRoom> findAllByUser(Pageable pageable, User user);
}
