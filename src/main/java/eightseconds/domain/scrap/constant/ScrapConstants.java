package eightseconds.domain.scrap.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ScrapConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EScrapServiceImpl {
        eAlreadyScrapExceptionMessage("이미 스크랩한 상품입니다.");

        private String value;
    }
}
