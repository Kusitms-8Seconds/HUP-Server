package eightseconds.domain.scrap.dto;

import eightseconds.domain.scrap.entity.Scrap;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "유저가 해당 상품을 스크랩하는 응답 객체")
public class ScrapRegisterResponse {

    private Long scrapId;

    public static ScrapRegisterResponse from(Scrap scrap) {
        return ScrapRegisterResponse.builder()
                .scrapId(scrap.getId())
                .build();
    }
}