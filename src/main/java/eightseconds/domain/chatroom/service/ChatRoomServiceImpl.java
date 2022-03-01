package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatroom.constant.ChatRoomConstants.EChatRoomServiceImpl;
import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.chatroom.exception.NotFoundChatRoomException;
import eightseconds.domain.chatroom.respoistory.ChatRoomRepository;
import eightseconds.domain.chatroom.respoistory.UserChatRoomRepository;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserService userService;

    @Override
    public List<ChatRoomResponse> getAllChatRooms(Long userId) {
        User user = userService.getUserByUserId(userId);
        List<UserChatRoom> listUserChatRoom = userChatRoomRepository.findAllByUser(user);
        List<ChatRoomResponse> chatRoomResponses = new ArrayList<>();
        for (UserChatRoom userChatRoom : listUserChatRoom) {
            Long id = userChatRoom.getChatRoom().getId();
            List<UserChatRoom> userIdAllByChatRoomId = userChatRoomRepository.findAllByChatRoomId(id);
            for (UserChatRoom tempUserChatRoom : userIdAllByChatRoomId) {
                if (userId == tempUserChatRoom.getUser().getId()) continue;
                else chatRoomResponses.add(ChatRoomResponse.from(tempUserChatRoom));
            }}
                return chatRoomResponses;
    }

    @Override
    public ChatRoom getChatRoomByChatId(Long chatId) {
        return validateChatId(chatId);
    }

    @Override
    @Transactional
    public void createChatRoom(PriceSuggestion priceSuggestion, Item item) {
        ChatRoom chatRoom = new ChatRoom();
        item.setChatRoom(chatRoom);
        chatRoomRepository.save(chatRoom);
        UserChatRoom userChatRoomByBidder = new UserChatRoom();
        UserChatRoom userChatRoomBySeller = new UserChatRoom();
        userChatRoomByBidder.setUser(priceSuggestion.getUser());
        userChatRoomBySeller.setUser(item.getUser());
        userChatRoomByBidder.setChatRoom(chatRoom);
        userChatRoomBySeller.setChatRoom(chatRoom);
        userChatRoomRepository.save(userChatRoomByBidder);
        userChatRoomRepository.save(userChatRoomBySeller);
    }


    /**
     * validate
     */
    private ChatRoom validateChatId(Long chatId) {
        return chatRoomRepository.findById(chatId).orElseThrow(() ->
                new NotFoundChatRoomException(EChatRoomServiceImpl.eNotFoundChatRoomExceptionMessage.getValue()));
    }


}
