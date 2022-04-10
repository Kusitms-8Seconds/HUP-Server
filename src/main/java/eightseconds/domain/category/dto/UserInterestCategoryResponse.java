package eightseconds.domain.category.dto;

import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.category.entity.UserCategory;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 유저의 관심 카테고리 응답 객체")
public class UserInterestCategoryResponse {
    List<ECategory> categories;

    public static UserInterestCategoryResponse from(List<UserCategory> userCategories) {
        List<ECategory> categories = new ArrayList<>();
        for (UserCategory userCategory : userCategories) {
            categories.add(userCategory.getCategory().getCategory());
        }
        return UserInterestCategoryResponse.builder()
                .categories(categories)
                .build();
    }
}
