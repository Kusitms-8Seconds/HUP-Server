package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomService {
    PaginationDto<List<ChatRoomResponse>> getAllChatRooms(Pageable pageable, Long userId);
    ChatRoom getChatRoomByChatId(Long chatId);
}
