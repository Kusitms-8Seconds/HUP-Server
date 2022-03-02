package eightseconds.domain.chatroom.controller;

import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.dto.IsEnterChatRoomRequest;
import eightseconds.domain.chatroom.dto.IsEnterChatRoomResponse;
import eightseconds.domain.chatroom.service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "해당 유저의 채팅방 입장여부 조회", notes = "해당 유저의 채팅방 입장여부를 조회합니다.")
    @PostMapping("/isEnter")
    public ResponseEntity<IsEnterChatRoomResponse> isEnter(@RequestBody IsEnterChatRoomRequest isEnterChatRoomRequest) {
        return ResponseEntity.ok(chatRoomService.isEnter(isEnterChatRoomRequest));
    }

}
