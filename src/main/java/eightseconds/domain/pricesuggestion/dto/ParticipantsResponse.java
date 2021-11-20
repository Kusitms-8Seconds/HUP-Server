package eightseconds.domain.pricesuggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParticipantsResponse {

    private int participantsCount;

    public static ParticipantsResponse from(int participantsCount) {
        return ParticipantsResponse.builder()
                .participantsCount(participantsCount)
                .build();
    }
}
