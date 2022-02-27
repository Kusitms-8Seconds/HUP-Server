package eightseconds.domain.chatmessage.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Setter
@AllArgsConstructor
@ApiModel(description = "채팅방의 메세지를 전달하기 위한 요청 객체")
public class ChatMessageRequest {

    @NotBlank(message = "메세지를 보내고자 하는 채팅방 id를 입력해주세요.")
    @ApiModelProperty(notes = "채팅방 id를 입력해 주세요.")
    private Long chatRoomId;

    @NotBlank(message = "메세지를 보내는 유저이름을 입력해주세요.")
    @ApiModelProperty(notes = "유저이름을 입력해 주세요.")
    private String userName;

    @NotBlank(message = "보내고자 하는 메세지를 입력해주세요.")
    @ApiModelProperty(notes = "메세지를 입력해주세요.ㄴ")
    private String message;
}
