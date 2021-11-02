package kusitms.domain.user.service;

import kusitms.domain.user.dto.SignUpRequest;
import kusitms.domain.user.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(SignUpRequest signUpRequest);
    Optional<User> getUserWithAuthorities(String loginId);
    Optional<User> getMyUserWithAuthorities();
}
