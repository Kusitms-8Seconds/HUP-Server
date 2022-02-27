package eightseconds.domain.chatroom.service;

import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.chatroom.respoistory.ChatRoomRepository;
import eightseconds.domain.chatroom.respoistory.UserChatRoomRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserService userService;

    @Override
    public PaginationDto<List<ChatRoomResponse>> getAllChatRooms(Pageable pageable, Long userId) {
        User user = userService.getUserByUserId(userId);
        Page<UserChatRoom> page = userChatRoomRepository.findAllByUser(pageable, user);
        List<ChatRoomResponse> data = page.get().map(ChatRoomResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }
}
