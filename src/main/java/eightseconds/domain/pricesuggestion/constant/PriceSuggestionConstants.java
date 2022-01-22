package eightseconds.domain.pricesuggestion.constant;

import lombok.Getter;

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
        eNotOnGoingExceptionMessage("경매 진행중인 상품이 아닙니다."),
        eAlreadySoldOutExceptionMessage("이미 팔린 상품입니다."),
        ePriorPriceSuggestionExceptionMessage("이전의 입찰가격이 지금의 입찰보다 높거나 같습니다.");
        private int size;
        private String value;

        EPriceSuggestionServiceImpl(int size) {this.size = size;}
        EPriceSuggestionServiceImpl(String value) {this.value = value;}
    }


}
