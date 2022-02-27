package eightseconds.domain.chatmessage.controller;

import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.service.ChatMessageService;
import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/chatMessages")
@Api(tags = "ChatRoom API")
public class ChatMessageApiController {

    private final ChatMessageService chatMessageService;

    @ApiOperation(value = "채팅방의 모든 메세지 조회", notes = "채팅방의 모든 메세지를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PaginationDto<List<ChatMessageResponse>>>> getAllChatMessages(@PageableDefault Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.ok(EntityModel.of(chatMessageService.getAllChatMessages(pageable, id)));
    }
}
