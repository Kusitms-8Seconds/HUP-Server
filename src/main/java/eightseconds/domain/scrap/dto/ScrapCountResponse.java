package eightseconds.domain.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ScrapCountResponse {

    private Long heart;

    public static ScrapCountResponse from(Long heart) {
        return ScrapCountResponse.builder()
                .heart(heart)
                .build();
    }
}
