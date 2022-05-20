package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.service.ChatMessageService;
import eightseconds.domain.chatroom.constant.ChatRoomConstants.EChatRoomServiceImpl;
import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.dto.CheckEntryRequest;
import eightseconds.domain.chatroom.dto.CheckEntryResponse;
import eightseconds.domain.chatroom.dto.DeleteChatRoomRequest;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.chatroom.exception.AlreadyEnterException;
import eightseconds.domain.chatroom.exception.NotFoundChatRoomException;
import eightseconds.domain.chatroom.exception.NotFoundUserChatRoomException;
import eightseconds.domain.chatroom.respoistory.ChatRoomRepository;
import eightseconds.domain.chatroom.respoistory.UserChatRoomRepository;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
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
        User user = this.userService.getUserByUserId(userId);
        List<UserChatRoom> listUserChatRoom = this.userChatRoomRepository.findAllByUser(user);
        validateUserChatRoomCountIsZero(listUserChatRoom);
        List<ChatRoomResponse> chatRoomResponses = new ArrayList<>();
        for (UserChatRoom userChatRoom : listUserChatRoom) {
            if(userChatRoom.getChatRoom().getUserChatRooms().size() == 2){
                for (UserChatRoom tempUserChatRoom : userChatRoom.getChatRoom().getUserChatRooms()) {
                    if(!userChatRoom.equals(tempUserChatRoom))
                        chatRoomResponses.add(ChatRoomResponse.from(userChatRoom, tempUserChatRoom));
            }} else chatRoomResponses.add(ChatRoomResponse.from(userChatRoom));}
        return chatRoomResponses;
    }

    public UserChatRoom getUserChatRoomByUserIdAndChatRoomId(Long userId, Long chatRoomId) {
        return validateUserIdAndChatRoomId(userId, chatRoomId);
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
        this.chatRoomRepository.save(chatRoom);
        UserChatRoom userChatRoomByBidder = new UserChatRoom();
        UserChatRoom userChatRoomBySeller = new UserChatRoom();
        userChatRoomByBidder.setUser(priceSuggestion.getUser());
        userChatRoomBySeller.setUser(item.getUser());
        userChatRoomByBidder.setChatRoom(chatRoom);
        userChatRoomBySeller.setChatRoom(chatRoom);
        this.userChatRoomRepository.save(userChatRoomByBidder);
        this.userChatRoomRepository.save(userChatRoomBySeller);
    }

    @Override
    public CheckEntryResponse checkEntry(CheckEntryRequest isEnterChatRoomRequest) {
        Long userId = isEnterChatRoomRequest.getUserId();
        Long chatRoomId = isEnterChatRoomRequest.getChatRoomId();
        UserChatRoom userChatRoom = validateUserIdAndChatRoomId(userId, chatRoomId);
        if(userChatRoom.isEntryCheck()) return CheckEntryResponse.from(true);
        else return CheckEntryResponse.from(false);
    }

    @Override
    public void deleteChatRoom(UserChatRoom userChatRoom) {
        this.userChatRoomRepository.delete(userChatRoom);
    }

    /**
     * validate
     */

    private ChatRoom validateChatId(Long chatId) {
        return this.chatRoomRepository.findById(chatId).orElseThrow(() ->
                new NotFoundChatRoomException());
    }

    public UserChatRoom validateIsEnter(Long userId, Long chatRoomId) {
        return this.userChatRoomRepository.findOneByUserIdAndChatRoomId(userId, chatRoomId).orElseThrow(() ->
                new NotFoundUserChatRoomException());
    }

    public UserChatRoom validateUserIdAndChatRoomId(Long userId, Long chatRoomId) {
        return this.userChatRoomRepository.findOneByUserIdAndChatRoomId(userId, chatRoomId).orElseThrow(() ->
                new NotFoundUserChatRoomException());
    }

    public void validateAlreadyEnter(Long userId, Long chatRoomId) {
        if(this.userChatRoomRepository.findOneByUserIdAndChatRoomId(userId, chatRoomId).get().isEntryCheck()){
            throw new AlreadyEnterException();
        }
    }

    private void validateUserChatRoomCountIsZero(List<UserChatRoom> listUserChatRoom) {
        if (listUserChatRoom.size() == 0) {
            throw new NotFoundUserChatRoomException();}
    }

}
