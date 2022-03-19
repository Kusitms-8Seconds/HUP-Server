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
@ApiModel(description = "해당 유저의 채팅방을 삭제하는 요청 객체")
public class DeleteChatRoomRequest {

    @NotNull(message = "채팅방 id를 입력해주세요.")
    @ApiModelProperty(notes = "채팅방 id를 입력해 주세요.")
    private Long chatRoomId;

    @NotNull(message = "유저 id를 입력해주세요.")
    @ApiModelProperty(notes = "유저 id를 입력해 주세요.")
    private Long userId;
}
