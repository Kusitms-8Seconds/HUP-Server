package eightseconds.domain.scrap.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ScrapConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EScrapApiController {
        eLocationIdPath("/{id}"),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        eUpdateMethod("update");
        private String value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ScrapExceptionList {
        ALREADY_SCRAP("S0001", HttpStatus.BAD_REQUEST, "이미 스크랩한 상품입니다."),
        NOT_EXISTING_SCRAP_OF_USER("S0002", HttpStatus.NOT_FOUND, "해당 유저의 스크랩 내역이 존재하지 않습니다."),
        NOT_FOUND_SCRAP("S0003", HttpStatus.NOT_FOUND, "존재하지 않는 스크랩입니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
