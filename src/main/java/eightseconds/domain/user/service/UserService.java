package eightseconds.domain.user.service;

import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.global.dto.DefaultResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    SignUpResponse saveUser(SignUpRequest signUpRequest);
    LoginResponse loginUser(LoginRequest loginRequest);
    User getUserByLoginId(String loginId);
    UserInfoResponse getUserInfoByUserId(Long userId);
    void deleteUserByUserId(Long userId);
    User getUserByUserId(Long userId);
    UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest);
    ReissueResponse reissueToken(ReissueRequest reissueRequest) throws Exception;
    UpdateProfileResponse updateProfileImage(MultipartFile file, String userId) throws IOException;
    FindLoginIdResponse findLoginId(FindLoginIdRequest findLoginIdRequest);
    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
    TokenInfoResponse validateLogin(LoginRequest loginRequest);
    User validateUserId(Long userId);
    void validateIsAlreadyRegisteredUser(User user);
    void validateAlreadyRegisteredUser(String loginId);
    DefaultResponse validateLoginId(String loginId);

}
