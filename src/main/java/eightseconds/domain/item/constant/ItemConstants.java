package eightseconds.domain.item.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
    @RequiredArgsConstructor
    public enum EItemServiceImpl{
        eInvalidItemSoldStatusExceptionMessage("유효하지 않은 상품판매상태입니다."),
        eInvalidCategoryExceptionMessage("유효하지 않은 카테고리입니다."),
        eNotFoundItemExceptionForCategoryMessage("해당 카테고리에 해당하는 상품이 없습니다."),
        eNotFoundItemExceptionForDefaultMessage("해당 아이디로 상품을 찾을 수 없습니다."),
        eNotOnGoingExceptionMessage("경매중인 상품이 아닙니다."),
        eNotSoldOutTimeExceptionMessage("낙찰 가능한 시간이 아닙니다."),
        eNotDesirableAuctionEndTimeExceptionMessage("경매종료일자가 현재시각보다 빠릅니다."),
        eNotPriceSuggestionContentExceptionMessage("경매입찰내역이 없습니다.");
        private final String value;
    }

    @Getter
    public enum EItem{
        eZero(0),
        eNotBidItemExceptionMessage("입찰 내역이 없습니다.");

        private int number;
        private String value;

        EItem(int number) {this.number = number;}
        EItem(String value) {this.value = value;}
    }
}
