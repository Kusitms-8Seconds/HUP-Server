package eightseconds.domain.scrap.dto;

import eightseconds.domain.scrap.entity.Scrap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class ScrapRegisterResponse {

    private Long scrapId;

    public static ScrapRegisterResponse from(Scrap scrap) {
        return ScrapRegisterResponse.builder()
                .scrapId(scrap.getId())
                .build();
    }
}