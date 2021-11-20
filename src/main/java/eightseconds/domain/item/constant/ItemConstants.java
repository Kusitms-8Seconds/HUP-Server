package eightseconds.domain.item.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ItemConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum EFileServiceImpl {
        BASE_DIR("user.dir"),
        IMAGES_DIR("images/"),
        FILE_NOT_FOUND_EXCEPTION_MESSAGE("파일이 존재하지 않습니다."),
        FILE_TO_SAVE_NOT_EXIST_EXCEPTION_MESSAGE("저장할 파일이 존재하지 않습니다.");

        private String message;
    }

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
}
