package eightseconds.domain.category.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ECategoryServiceImpl {
        eNotFoundCategoryException("해당하는 카테고리를 찾을 수 없습니다.");
        private String value;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ECategory {
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
        public static ECategory from(String s) {
            return ECategory.valueOf(s);
        }
    }
}
