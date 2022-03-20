package eightseconds.domain.chatroom.controller;

import eightseconds.domain.chatroom.dto.DeleteChatRoomRequest;
import eightseconds.domain.chatroom.service.ChatRoomService;
import eightseconds.global.dto.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatRoomSTOMPController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRoomService chatRoomService;

    @MessageMapping(value = "/chatRoom/out")
    public void outChatRoom(DeleteChatRoomRequest deleteChatRoomRequest){
        DefaultResponse defaultResponse = chatRoomService.deleteChatRoom(deleteChatRoomRequest);
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + deleteChatRoomRequest.getChatRoomId(), defaultResponse);
    }
}
