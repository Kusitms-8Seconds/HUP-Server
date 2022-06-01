package eightseconds.domain.category.service;

import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.category.constant.CategoryConstants.ECategoryServiceImpl;
import eightseconds.domain.category.dto.InterestCategoryRequest;
import eightseconds.domain.category.dto.UserInterestCategoryResponse;
import eightseconds.domain.category.entity.Category;
import eightseconds.domain.category.entity.UserCategory;
import eightseconds.domain.category.exeception.NotFoundCategoryException;
import eightseconds.domain.category.exeception.NotFoundUserInterestCategoryException;
import eightseconds.domain.category.repository.CategoryRepository;
import eightseconds.domain.category.repository.UserCategoryRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final UserService userService;

    @Override
    public Category getCategoryByEItemCategory(ECategory eCategory) {
        return this.categoryRepository.findByCategory(eCategory).orElseThrow(()
                -> new NotFoundCategoryException());
    }

    @Override
    public DefaultResponse createUserCategories(InterestCategoryRequest interestCategoryRequest) {
        Long userId = interestCategoryRequest.getUserId();
        User user = this.userService.validateUserId(userId);
        List<ECategory> eCategoryList = interestCategoryRequest.getECategoryList();
        deleteAlreadyUserCategories(userId);
        for (ECategory eCategory : eCategoryList) {
            UserCategory userCategory = UserCategory.builder()
                    .category(getCategoryByEItemCategory(eCategory))
                    .build();
            user.setCategory(userCategory);
            this.userCategoryRepository.save(userCategory);
        }
        return DefaultResponse.from(ECategoryServiceImpl.eFinishChangeUserCategoriesMessage.getValue());
    }

    @Override
    public UserInterestCategoryResponse getUserCategories(Long userId) {
        this.userService.validateUserId(userId);
        List<UserCategory> userCategories = this.userCategoryRepository.findAllByUserId(userId).get();
        if(userCategories.size()==0){ throw new NotFoundUserInterestCategoryException();}
        return UserInterestCategoryResponse.from(userCategories);
    }

    private void deleteAlreadyUserCategories(Long userId) {
        this.userCategoryRepository.findAllByUserId(userId).ifPresent(c -> {
            this.userCategoryRepository.deleteAll(c);
        });
    }
}
