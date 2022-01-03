package eightseconds.domain.user.service;

import eightseconds.domain.user.dto.LoginRequest;
import eightseconds.domain.user.dto.SignUpRequest;
import eightseconds.domain.user.dto.SignUpResponse;
import eightseconds.domain.user.dto.UserInfoResponse;
import eightseconds.domain.user.entity.User;

import java.util.Optional;

public interface UserService {

    SignUpResponse saveUser(SignUpRequest signUpRequest);
    Optional<User> getUserWithAuthorities(String loginId);
    Optional<User> getMyUserWithAuthorities();
    String validationLogin(LoginRequest loginRequest);
    User getUserByLoginId(String loginId);
    UserInfoResponse getUserInfoByUserId(Long userId);
    User getUserByUserId(Long userId);
    User validateUserId(Long userId);
}
