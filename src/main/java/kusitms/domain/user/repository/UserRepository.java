package kusitms.domain.user.repository;

import kusitms.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities") // Lazy 조회가 아니라 Eager조회로 authorities 테이블 데이터를 같이 가져옴
    Optional<User> findOneWithAuthoritiesByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
