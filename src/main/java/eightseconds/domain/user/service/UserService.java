package eightseconds.domain.user.service;

import eightseconds.domain.user.dto.LoginRequest;
import eightseconds.domain.user.dto.SignUpRequest;
import eightseconds.domain.user.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(SignUpRequest signUpRequest);
    Optional<User> getUserWithAuthorities(String loginId);
    Optional<User> getMyUserWithAuthorities();
    String validationLogin(LoginRequest loginRequest);
}
