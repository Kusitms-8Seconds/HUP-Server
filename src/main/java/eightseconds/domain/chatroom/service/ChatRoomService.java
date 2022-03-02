package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.dto.IsEnterChatRoomRequest;
import eightseconds.domain.chatroom.dto.IsEnterChatRoomResponse;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import org.springframework.data.domain.Pageable;

import java.rmi.AlreadyBoundException;
import java.util.List;

public interface ChatRoomService {
    List<ChatRoomResponse> getAllChatRooms(Long userId);
    ChatRoom getChatRoomByChatId(Long chatId);
    void createChatRoom(PriceSuggestion priceSuggestion, Item item);
    IsEnterChatRoomResponse isEnter(IsEnterChatRoomRequest isEnterChatRoomRequest);
    UserChatRoom validateIsEnter(Long userId, Long chatRoomId);
    void validateAlreadyEnter(Long userId, Long chatRoomId);
}
