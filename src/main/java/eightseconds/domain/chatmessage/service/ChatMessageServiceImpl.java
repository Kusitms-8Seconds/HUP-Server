package eightseconds.domain.chatmessage.service;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.entity.ChatMessage;
import eightseconds.domain.chatmessage.respoistory.ChatMessageRepository;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.service.ChatRoomService;
import eightseconds.domain.user.dto.UserInfoResponse;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService{

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    @Override
    public PaginationDto<List<ChatMessageResponse>> getAllChatMessages(Pageable pageable, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatId(chatRoomId);
        Page<ChatMessage> page = chatMessageRepository.findByChatRoom(pageable, chatRoom);
        List<ChatMessageResponse> data = page.get().map(ChatMessageResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    @Transactional
    public ChatMessageResponse createMessage(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(chatMessageRequest.getMessage());
        User user = userService.getUserByUserId(chatMessageRequest.getUserId());
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatId(chatMessageRequest.getChatRoomId());
        chatMessage.setUser(user);
        chatMessage.setChatRoom(chatRoom);
        chatMessageRepository.save(chatMessage);
        return ChatMessageResponse.from(chatMessage);
    }
}
