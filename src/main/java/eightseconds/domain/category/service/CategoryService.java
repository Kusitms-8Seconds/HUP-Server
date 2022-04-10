package eightseconds.domain.category.service;

import eightseconds.domain.category.entity.Category;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;

public interface CategoryService {
    Category getCategoryByEItemCategory(ECategory eCategory);
}
