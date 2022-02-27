package eightseconds.domain.chatmessage.service;

import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.entity.ChatMessage;
import eightseconds.domain.chatmessage.respoistory.ChatMessageRepository;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.service.ChatRoomService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService{

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public PaginationDto<List<ChatMessageResponse>> getAllChatMessages(Pageable pageable, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatId(chatRoomId);
        Page<ChatMessage> page = chatMessageRepository.findByChatRoom(chatRoom);
        List<ChatMessageResponse> data = page.get().map(ChatMessageResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }
}
