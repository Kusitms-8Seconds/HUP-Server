package eightseconds.domain.chatroom.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 유저의 채팅방 입장 여부 조회 응답 객체")
public class CheckEntryResponse {

    private boolean isEnter;

    public static CheckEntryResponse from(boolean isEnter) {
        return CheckEntryResponse.builder()
                .isEnter(isEnter)
                .build();
    }
}
