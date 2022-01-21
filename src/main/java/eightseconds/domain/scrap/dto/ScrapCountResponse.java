package eightseconds.domain.scrap.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 상품의 좋아요 수를 조회하는 응답 객체")
public class ScrapCountResponse {

    private Long heart;

    public static ScrapCountResponse from(Long heart) {
        return ScrapCountResponse.builder()
                .heart(heart)
                .build();
    }
}
