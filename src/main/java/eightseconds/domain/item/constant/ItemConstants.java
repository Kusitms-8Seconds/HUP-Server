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
    public enum EItemCategory{
        eDigital("디지털 기기"),
        eHouseHoldAppliance("생활가전"),
        eFurnitureAndInterior("가구/인테리어"),
        eChildren("유아동"),
        eDailyLifeAndProcessedFood("생활/가공식품"),
        eChildrenBooks("유아도서"),
        eSportsAndLeisure("스포츠/레저"),
        eMerchandiseForWoman("여성잡화"),
        eWomenClothing("여성의류"),
        eManFashionAndMerchandise("남성패션/잡화"),
        eGameAndHabit("게임/취미"),
        eBeauty("뷰티/미용"),
        ePetProducts("반려동물용품"),
        eBookTicketAlbum("도서/티켓/음반"),
        ePlant("식물");

        private String name;

        @JsonCreator
        public static EItemCategory from(String s) {
            return EItemCategory.valueOf(s);
        }
    }

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
