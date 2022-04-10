package eightseconds.domain.category.repository;

import eightseconds.domain.category.entity.Category;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategory(ECategory category);
}
