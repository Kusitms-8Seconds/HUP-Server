package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.dto.CheckEntryRequest;
import eightseconds.domain.chatroom.dto.CheckEntryResponse;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomResponse> getAllChatRooms(Long userId);
    ChatRoom getChatRoomByChatId(Long chatId);
    void createChatRoom(PriceSuggestion priceSuggestion, Item item);
    CheckEntryResponse checkEntry(CheckEntryRequest isEnterChatRoomRequest);
    UserChatRoom validateIsEnter(Long userId, Long chatRoomId);
    void validateAlreadyEnter(Long userId, Long chatRoomId);
    void deleteChatRoom(UserChatRoom deleteChatRoomRequest);
    UserChatRoom getUserChatRoomByUserIdAndChatRoomId(Long id, Long id1);

}
