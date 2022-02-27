package eightseconds.domain.chatmessage.controller;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class ChatMessageSTOMPController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(value = "/chatRoom/enter")
    public void enterChatRoom(ChatMessageRequest chatMessageRequest){
        chatMessageRequest.setMessage(chatMessageRequest.getUserName() + "님이 채팅방에 참여하였습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + chatMessageRequest.getChatRoomId(), chatMessageRequest);
    }

    @MessageMapping(value = "/chatRoom/send")
    public void sendMessageToChatRoom(ChatMessageRequest message){
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + message.getChatRoomId(), message);
    }
}
