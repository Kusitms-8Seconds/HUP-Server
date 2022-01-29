package eightseconds.domain.user.service;

import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.global.dto.DefaultResponse;

import java.util.Optional;

public interface UserService {

    SignUpResponse saveUser(SignUpRequest signUpRequest);
    String validateLogin(LoginRequest loginRequest);
    User getUserByLoginId(String loginId);
    UserInfoResponse getUserInfoByUserId(Long userId);
    void deleteUserByUserId(Long userId);
    User getUserByUserId(Long userId);
    User validateUserId(Long userId);
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);
    void validateIsAlreadyRegisteredUser(String email);
    DefaultResponse validateLoginId(String loginId);
}
