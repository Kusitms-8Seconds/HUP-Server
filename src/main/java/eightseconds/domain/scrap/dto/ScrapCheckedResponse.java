package eightseconds.domain.scrap.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "유저가 해당 상품을 스크랩중인지 조회하는 응답 객체")
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
