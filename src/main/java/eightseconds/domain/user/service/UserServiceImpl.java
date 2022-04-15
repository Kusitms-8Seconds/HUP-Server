package eightseconds.domain.user.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.service.FileService;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.app.*;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.jwt.TokenProvider;
import eightseconds.infra.email.entity.EmailAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;
    private final FileService fileService;

    @Override
    public SignUpResponse saveUser(SignUpRequest signUpRequest) {
        if (userRepository.findOneWithAuthoritiesByLoginId(signUpRequest.getLoginId()).orElse(null) != null)
                throw new AlreadyRegisteredUserException(EUserServiceImpl.eAlreadyRegisteredUserExceptionMessage.getValue());
        validateAlreadyRegisteredEmail(signUpRequest.getEmail());
        User savedUser = this.userRepository.save(User.toEntity(signUpRequest, passwordEncoder));
        return SignUpResponse.from(savedUser.getId(), savedUser.getLoginId(), EUserServiceImpl.eSuccessSignUpMessage.getValue());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String loginId) {
        return userRepository.findOneWithAuthoritiesByLoginId(loginId)
                .map(user -> createUser(loginId, user))
                .orElseThrow(() -> new UsernameNotFoundException(loginId + EUserServiceImpl.eUsernameNotFoundExceptionMessage.getValue()));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) throw new UserNotActivatedException(username + EUserServiceImpl.eUserNotActivatedExceptionMessage.getValue());

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getLoginId(),
                user.getPassword(),
                grantedAuthorities);
    }

    @Transactional
    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = getUserByLoginId(loginRequest.getLoginId());
        //validateEmailAuth(user);
        TokenInfoResponse tokenInfoResponse = validateLogin(loginRequest);
        updateTargetToken(user, loginRequest.getTargetToken());
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    public void updateTargetToken(User user, String targetToken) {
        user.setTargetToken(targetToken);
    }

    public User getUserByLoginId(String loginId) {
        Optional<User> user = userRepository.findUserByLoginId(loginId);
        return user.get();
    }

    public UserInfoResponse getUserInfoByUserId(Long userId) {
        User user = validateUserId(userId);
        return UserInfoResponse.from(user);
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        validateUserId(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = validateUserId(updateUserRequest.getUserId());
        updateUserRequest.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        User updatedUser = userRepository.save(user.updateUserByUpdateUserRequest(updateUserRequest));
        return UpdateUserResponse.from(updatedUser);
    }

    public User getUserByUserId(Long userId) {
        return validateUserId(userId);
    }

    public ReissueResponse reissueToken(ReissueRequest reissue){
        if (!tokenProvider.validateRefreshToken(reissue.getRefreshToken())) {
            throw new NotValidRefreshTokenException(EUserServiceImpl.eNotValidRefreshTokenExceptionMessage.getValue());}
        Authentication authentication = tokenProvider.getAuthentication(reissue.getAccessToken());
        String refreshToken = (String) redisTemplate.opsForValue().get(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName());
        if(ObjectUtils.isEmpty(refreshToken))
            throw new WrongRefreshTokenRequestException(EUserServiceImpl.eWrongRefreshTokenRequestExceptionMessage.getValue());
        if(!refreshToken.equals(reissue.getRefreshToken()))
            throw new NotMatchRefreshTokenException(EUserServiceImpl.eNotMatchRefreshTokenExceptionMessage.getValue());
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(authentication);
        redisTemplate.opsForValue()
                .set(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName(),
                        tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return ReissueResponse.from(tokenInfoResponse.getGrantType(), tokenInfoResponse.getAccessToken(),
                tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime());
    }

    @Transactional
    public DefaultResponse logout(LogoutRequest logoutRequest){
        if (!tokenProvider.validateToken(logoutRequest.getAccessToken()))
            throw new NotValidAccessTokenException(EUserServiceImpl.eNotValidAccessTokenExceptionMessage.getValue());
        Authentication authentication = tokenProvider.getAuthentication(logoutRequest.getAccessToken());
        if (redisTemplate.opsForValue().get(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName()) != null)
            redisTemplate.delete(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName());
        Long expiration = tokenProvider.getExpiration(logoutRequest.getAccessToken());
        redisTemplate.opsForValue()
                .set(logoutRequest.getAccessToken(), EUserServiceImpl.eLogout.getValue(), expiration, TimeUnit.MILLISECONDS);
        deleteTargetToken(userRepository.findUserByLoginId(authentication.getName()).get());
        return DefaultResponse.from(EUserServiceImpl.eLogoutMessage.getValue());
    }

    public void deleteTargetToken(User user) {
        user.setTargetToken(null);
    }

    @Override
    @Transactional
    public UpdateProfileResponse updateProfileImage(MultipartFile file, String userId) throws IOException {
        User user = validateUserId(Long.valueOf(userId));
        String fileURL;
        if (file != null || !file.isEmpty()) {
            MyFile myFile = fileService.saveSingleFile(file);
            fileURL = EUserServiceImpl.eBaseFileURL.getValue() + myFile.getFilename();
        } else fileURL = EUserServiceImpl.eBasePicture.getValue();

        user.setPicture(fileURL);
        return UpdateProfileResponse.from(fileURL);
    }

    @Override
    public FindLoginIdResponse findLoginId(FindLoginIdRequest findLoginIdRequest) {
        return FindLoginIdResponse.from(this.userRepository.findByEmailAndLoginType(findLoginIdRequest.getEmail(), ELoginType.eApp)
                .orElseThrow(NotFoundRegisteredEmailException::new));
    }

    @Override
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        this.validateSamePassword(resetPasswordRequest);
        User user = this.getUserByUserId(resetPasswordRequest.getUserId());
        this.validateEmailAuthForResetPassword(user);
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        user.getEmailAuth().setPasswordCheck(false);
        return ResetPasswordResponse.from(user);
    }


    /**
     * validate
     */

    public User validateUserId(Long userId){
        return userRepository.findById(userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundUserException(EUserServiceImpl.eNotFoundUserExceptionMessage.getValue()));
    }

    public TokenInfoResponse validateLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(authentication);
        redisTemplate.opsForValue()
                .set(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName(),
                        tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return tokenInfoResponse;
    }

//    private void validateEmailAuth(User user) {
//        if (!user.isEmailAuthActivated()) throw new NotActivatedEmailAuthException(EUserServiceImpl.eNotActivatedEmailAuthExceptionMessage.getValue());
//    }

    public User validateNotRegisteredEmail(String email) {
        return userRepository.findByEmailAndLoginType(email, ELoginType.eApp)
                .orElseThrow(() -> new NotFoundRegisteredUserException(EUserServiceImpl
                        .eNotFoundRegisteredUserExceptionMessage.getValue()));
    }

    public void validateAlreadyRegisteredEmail(String email) {
        userRepository.findByEmailAndLoginType(email, ELoginType.eApp)
                .ifPresent((u -> { throw new AlreadyRegisteredEmailException(EUserServiceImpl
                        .eAlreadyRegisteredEmailExceptionMessage.getValue()); }));
    }

    public void validateIsAlreadyRegisteredUser(User user) {
        if(user.isEmailAuthActivated()) throw new AlreadyRegisteredUserException(EUserServiceImpl
                        .eAlreadyRegisteredUserExceptionMessage.getValue());
    }

    @Override
    public DefaultResponse validateLoginId(String loginId) {
        userRepository.findUserByLoginId(loginId)
                .ifPresent((u -> { throw new AlreadyRegisteredUserException(EUserServiceImpl
                        .eAlreadyRegisteredLoginIdExceptionMessage.getValue()); }));
        return DefaultResponse.from(EUserServiceImpl.eNotDuplicatedLoginIdMessage.getValue());
    }

    private void validateSamePassword(ResetPasswordRequest resetPasswordRequest) {
        if(!resetPasswordRequest.getPassword().equals(resetPasswordRequest.getCheckPassword())){
            throw new NotSamePasswordException();}
    }

    private void validateEmailAuthForResetPassword(User user) {
        if (user.getEmailAuth()==null) throw new NotSendEmailException();
        else if(!user.getEmailAuth().isPasswordCheck()) throw new NotAuthenticationForPasswordException();}

}