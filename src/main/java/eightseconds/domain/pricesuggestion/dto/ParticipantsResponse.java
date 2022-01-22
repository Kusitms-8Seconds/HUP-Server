package eightseconds.domain.pricesuggestion.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 상품의 참여자수를 조회하기 위한 응답 객체")
public class ParticipantsResponse {

    private int participantsCount;

    public static ParticipantsResponse from(int participantsCount) {
        return ParticipantsResponse.builder()
                .participantsCount(participantsCount)
                .build();
    }
}
