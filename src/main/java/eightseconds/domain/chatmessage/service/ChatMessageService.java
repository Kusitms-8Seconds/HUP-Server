package eightseconds.domain.chatmessage.service;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatroom.dto.DeleteChatRoomRequest;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.rmi.AlreadyBoundException;
import java.util.List;

public interface ChatMessageService {
    PaginationDto<List<ChatMessageResponse>> getAllChatMessages(Pageable pageable, Long chatRoomId);
    ChatMessageResponse createSendMessage(ChatMessageRequest chatMessageRequest);
    ChatMessageResponse createEnterMessage(ChatMessageRequest chatMessageRequest);
    ChatMessageResponse deleteChatRoom(DeleteChatRoomRequest deleteChatRoomRequest);
}
