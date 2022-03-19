package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.dto.CheckEntryRequest;
import eightseconds.domain.chatroom.dto.CheckEntryResponse;
import eightseconds.domain.chatroom.dto.DeleteChatRoomRequest;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.global.dto.DefaultResponse;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomResponse> getAllChatRooms(Long userId);
    ChatRoom getChatRoomByChatId(Long chatId);
    void createChatRoom(PriceSuggestion priceSuggestion, Item item);
    CheckEntryResponse checkEntry(CheckEntryRequest isEnterChatRoomRequest);
    UserChatRoom validateIsEnter(Long userId, Long chatRoomId);
    void validateAlreadyEnter(Long userId, Long chatRoomId);
    DefaultResponse deleteChatRoom(DeleteChatRoomRequest deleteChatRoomRequest);

}
