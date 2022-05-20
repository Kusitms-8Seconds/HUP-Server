package eightseconds.domain.item.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ItemConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EItemSoldStatus{
        eNew("새로운 상품"),
        eOnGoing("경매중인 상품"),
        eSoldOut("판매 완료");

        private String name;

        @JsonCreator
        public static EItemSoldStatus from(String s) {
            return EItemSoldStatus.valueOf(s);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum EItemApiController{
        eLocationIdPath("/{id}"),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        eUpdateMethod("update");
        private final String value;
    }

    @Getter
    public enum EItem{
        eZero(0);

        private int number;
        private String value;

        EItem(int number) {this.number = number;}
        EItem(String value) {this.value = value;}
    }

    @Getter
    @RequiredArgsConstructor
    public enum ItemExceptionList {
        INVALID_CATEGORY("I0001", HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리입니다."),
        INVALID_ITEM_SOLD_STATUS("I0002", HttpStatus.BAD_REQUEST, "유효하지 않은 상품판매상태입니다."),
        NOT_BID_ITEM("I0003", HttpStatus.CONFLICT, "입찰 내역이 없습니다."),
        NOT_FOUND_ITEM_FOR_CATEGORY("I0004", HttpStatus.NOT_FOUND, "해당 카테고리에 해당하는 상품이 없습니다."),
        NOT_FOUND_ITEM_FOR_DEFALUT("I0005", HttpStatus.NOT_FOUND, "해당 아이디로 상품을 찾을 수 없습니다."),
        NOT_SOLD_OUT_TIME("I0006", HttpStatus.CONFLICT, "낙찰 가능한 시간이 아닙니다."),
        NOT_DESIRABLE_AUCTION_END_TIME("I0007", HttpStatus.CONFLICT, "경매종료일자가 현재시각보다 빠릅니다."),
        NOT_PRICE_SUGGESTION_CONTENT("I0008", HttpStatus.CONFLICT, "경매입찰내역이 없습니다."),
        NOT_ON_GOING("I0009", HttpStatus.CONFLICT, "경매 진행중인 상품이 아닙니다."),
        NOT_FOUND_ITEM("I0010", HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
