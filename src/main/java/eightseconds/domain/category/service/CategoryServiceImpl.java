package eightseconds.domain.category.service;

import eightseconds.domain.category.constant.CategoryConstants.ECategoryServiceImpl;
import eightseconds.domain.category.entity.Category;
import eightseconds.domain.category.exeception.NotFoundCategoryException;
import eightseconds.domain.category.repository.CategoryRepository;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByEItemCategory(ECategory eCategory) {
        return this.categoryRepository.findByCategory(eCategory).orElseThrow(()
                -> new NotFoundCategoryException(ECategoryServiceImpl.eNotFoundCategoryException.getValue()));
    }
}
