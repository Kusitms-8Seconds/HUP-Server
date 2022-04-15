package eightseconds.domain.user.repository;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLoginId(String loginId);
    Optional<User> findUserByLoginId(String loginId);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long id);
    Optional<User> findByEmailAndLoginType(String email, ELoginType loginType);
    Optional<User> findByEmailAndLoginTypeAndEmailAuthActivated(String email, ELoginType loginType, boolean emailAuthActivated);
    void deleteById(Long userId);

}
