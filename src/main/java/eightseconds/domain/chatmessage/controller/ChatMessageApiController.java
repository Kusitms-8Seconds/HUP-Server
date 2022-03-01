package eightseconds.domain.chatmessage.controller;

import eightseconds.domain.chatmessage.dto.ChatMessageRequest;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.service.ChatMessageService;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/chatMessages")
@Api(tags = "ChatMessage API")
public class ChatMessageApiController {

    private final ChatMessageService chatMessageService;

    @ApiOperation(value = "채팅방의 모든 메세지 조회", notes = "채팅방의 모든 메세지를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PaginationDto<List<ChatMessageResponse>>>> getAllChatMessages(@PageableDefault Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.ok(EntityModel.of(chatMessageService.getAllChatMessages(pageable, id)));
    }

    @ApiOperation(value = "입장 메세지 만들기 테스트", notes = "메세지 만들기 테스트")
    @PostMapping("/enterTest")
    public ResponseEntity<ChatMessageResponse> createEnterMessage(@RequestBody ChatMessageRequest chatMessageRequest) {
        return ResponseEntity.ok(chatMessageService.createEnterMessage(chatMessageRequest));
    }

    @ApiOperation(value = "보내는 메세지 만들기 테스트", notes = "메세지 만들기 테스트")
    @PostMapping("/sendTest")
    public ResponseEntity<ChatMessageResponse> createSendMessage(@RequestBody ChatMessageRequest chatMessageRequest) {
        return ResponseEntity.ok(chatMessageService.createSendMessage(chatMessageRequest));
    }
}
