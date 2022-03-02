package eightseconds.domain.chatroom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "해당 유저의 채팅방 입장 여부 조회 요청 객체")
public class IsEnterChatRoomRequest {

    @NotNull(message = "메세지를 보내고자 하는 채팅방 id를 입력해주세요.")
    @ApiModelProperty(notes = "채팅방 id를 입력해 주세요.")
    private Long chatRoomId;

    @NotNull(message = "메세지를 보내는 유저 id를 입력해주세요.")
    @ApiModelProperty(notes = "유저 id를 입력해 주세요.")
    private Long userId;
}
