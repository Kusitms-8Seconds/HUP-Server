package eightseconds.domain.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScrapCheckedResponse {

    private boolean isChecked;
    private Long scrapId;

    public static ScrapCheckedResponse from(boolean isChecked, Long scrapId) {
        return ScrapCheckedResponse.builder()
                .isChecked(isChecked)
                .scrapId(scrapId)
                .build();
    }
}
