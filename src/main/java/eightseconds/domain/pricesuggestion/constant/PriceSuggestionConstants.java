package eightseconds.domain.pricesuggestion.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class PriceSuggestionConstants {

    @Getter
    public enum EPriceSuggestionApiController{
        eLocationIdPath("/{id}"),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        eUpdateMethod("update"),
        ePageableOne(1);
        private String value;
        private int page;

        EPriceSuggestionApiController(String value) {this.value = value;}
        EPriceSuggestionApiController(int page) {this.page = page;}
    }

    @Getter
    public enum EPriceSuggestionServiceImpl{
        eZero(0),
        eNotOnGoingExceptionMessage("경매 진행중인 상품이 아닙니다.");
        private int size;
        private String value;

        EPriceSuggestionServiceImpl(int size) {this.size = size;}
        EPriceSuggestionServiceImpl(String value) {this.value = value;}
    }

    @Getter
    @RequiredArgsConstructor
    public enum PriceSuggestionExceptionList {
        ALREADY_SOLD_OUT("P0001", HttpStatus.CONFLICT, "이미 팔린 상품입니다."),
        AUCTION_CLOSING_TIME("P0002", HttpStatus.CONFLICT, "상품의 경매 종료 시간을 초과했습니다."),
        INIT_PRICE("P0003", HttpStatus.CONFLICT, "상품 초기가격보다 입찰가격이 작을 수 없습니다."),
        NOT_FOUND_PRICE_SUGGESTION("P0004", HttpStatus.NOT_FOUND, "해당 id로 가격입찰내역을 찾을 수 없습니다."),
        PRIOR_PRICE_SUGGESTION("P0005", HttpStatus.CONFLICT, "이전의 입찰가격이 지금의 입찰보다 높거나 같습니다."),
        SAME_USER_ID("P0006", HttpStatus.CONFLICT, "자신이 등록한 상품을 입찰할 수 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }


}
