package eightseconds.domain.category.service;

import eightseconds.domain.category.dto.InterestCategoryRequest;
import eightseconds.domain.category.dto.UserInterestCategoryResponse;
import eightseconds.domain.category.entity.Category;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.global.dto.DefaultResponse;

public interface CategoryService {
    Category getCategoryByEItemCategory(ECategory eCategory);
    DefaultResponse createUserCategories(InterestCategoryRequest interestCategoryRequest);
    UserInterestCategoryResponse getUserCategories(Long userId);

}
