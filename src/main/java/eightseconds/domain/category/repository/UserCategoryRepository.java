package eightseconds.domain.category.repository;

import eightseconds.domain.category.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    Optional<List<UserCategory>> findAllByUserId(Long userId);
}
