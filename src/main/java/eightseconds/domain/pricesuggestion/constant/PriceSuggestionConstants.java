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
        ePriorPriceSuggestionExceptionMessage("이전의 입찰가격이 지금의 입찰보다 높거나 같습니다."),
        eNotFoundPriceSuggestionExceptionMessage("해당 id로 가격입찰내역을 찾을 수 없습니다."),
        eSameUserIdExceptionMessage("자신이 등록한 상품을 입찰할 수 없습니다."),
        eInitPriceExceptionMessage("상품 초기가격보다 입찰가격이 작을 수 없습니다."),
        eAuctionClosingTimeExceptionMessage("상품의 경매 종료 시간을 초과했습니다.");
        private int size;
        private String value;

        EPriceSuggestionServiceImpl(int size) {this.size = size;}
        EPriceSuggestionServiceImpl(String value) {this.value = value;}
    }


}
