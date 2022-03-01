package eightseconds.domain.chatroom.controller;

import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.service.ChatRoomService;
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
@RequestMapping("api/v1/chatRooms")
@Api(tags = "ChatRoom API")
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;

    @ApiOperation(value = "유저의 모든 채팅방 조회", notes = "유저의 모든 채팅방을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<List<ChatRoomResponse>> getAllChatRooms(@PathVariable Long id) {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms(id));
    }

}
