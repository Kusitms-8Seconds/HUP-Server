package eightseconds.domain.chatmessage.controller;

import eightseconds.domain.chatmessage.constant.ChatMessageConstants.EChatMessageSTOMPController;
import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.service.ChatMessageService;
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
    private final ChatMessageService chatMessageService;

    @MessageMapping(value = "/chatRoom/enter")
    public void enterChatRoom(ChatMessageRequest chatMessageRequest){
        chatMessageRequest.setMessage(chatMessageRequest.getUserName() + EChatMessageSTOMPController.eChatRoomEnterMessage.getValue());
        ChatMessageResponse chatMessageResponse = chatMessageService.createMessage(chatMessageRequest);
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + chatMessageRequest.getChatRoomId(), chatMessageResponse);
    }

    @MessageMapping(value = "/chatRoom/send")
    public void sendMessageToChatRoom(ChatMessageRequest message){
        ChatMessageResponse chatMessageResponse = chatMessageService.createMessage(message);
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + message.getChatRoomId(), chatMessageResponse);
    }
}
