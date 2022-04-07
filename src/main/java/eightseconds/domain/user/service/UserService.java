package eightseconds.domain.user.service;

import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.global.dto.DefaultResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    SignUpResponse saveUser(SignUpRequest signUpRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    TokenInfoResponse validateLogin(LoginRequest loginRequest);
    User getUserByLoginId(String loginId);
    UserInfoResponse getUserInfoByUserId(Long userId);
    void deleteUserByUserId(Long userId);
    User getUserByUserId(Long userId);
    User validateUserId(Long userId);
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);
    void validateIsAlreadyRegisteredUser(User user);
    DefaultResponse validateLoginId(String loginId);
    ReissueResponse reissueToken(ReissueRequest reissueRequest) throws Exception;
    UpdateProfileResponse updateProfileImage(MultipartFile file, String userId) throws IOException;
}
