package eightseconds.domain.scrap.dto;

import eightseconds.global.dto.DefaultResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class ScrapRegisterResponse {

    private String message;
    private Long scrapId;

    private ScrapRegisterResponse(String message, Long scrapId) {
        this.message = message;
        this.scrapId = scrapId;
    }

    public static ScrapRegisterResponse from(String message, Long scrapId) {
        return new ScrapRegisterResponse(message, scrapId);
    }
}