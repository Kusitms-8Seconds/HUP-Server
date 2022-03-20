package eightseconds.domain.chatmessage.controller;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.service.ChatMessageService;
import eightseconds.domain.chatroom.dto.DeleteChatRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ChatMessageSTOMPController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping(value = "/chatRoom/enter")
    public void enterChatRoom(ChatMessageRequest chatMessageRequest){
        ChatMessageResponse chatMessageResponse = chatMessageService.createEnterMessage(chatMessageRequest);
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + chatMessageRequest.getChatRoomId(), chatMessageResponse);
    }

    @MessageMapping(value = "/chatRoom/send")
    public void sendMessageToChatRoom(ChatMessageRequest chatMessageRequest){
        ChatMessageResponse chatMessageResponse = chatMessageService.createSendMessage(chatMessageRequest);
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + chatMessageRequest.getChatRoomId(), chatMessageResponse);
    }

    @MessageMapping(value = "/chatRoom/out")
    public void outChatRoom(DeleteChatRoomRequest deleteChatRoomRequest){
        ChatMessageResponse chatMessageResponse = chatMessageService.deleteChatRoom(deleteChatRoomRequest);
        simpMessagingTemplate.convertAndSend("/sub/chatRoom/" + deleteChatRoomRequest.getChatRoomId(), chatMessageResponse);
    }
}
