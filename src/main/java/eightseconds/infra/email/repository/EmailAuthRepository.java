package eightseconds.infra.email.repository;

import eightseconds.infra.email.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    Optional<EmailAuth> findByAuthCode(String authCode);
    Optional<EmailAuth> findByEmail(String email);

}
