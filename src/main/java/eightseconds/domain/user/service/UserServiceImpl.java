package eightseconds.domain.user.service;

import eightseconds.domain.myfile.service.MyFileService;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.app.NotFoundUserException;
import eightseconds.domain.user.exception.app.*;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;
    private final MyFileService myFileService;

    @Override
    public SignUpResponse saveUser(SignUpRequest signUpRequest) {
        this.validateAlreadyRegisteredUser(signUpRequest.getLoginId());
        this.validateAlreadyRegisteredEmail(signUpRequest.getEmail());
        User savedUser = this.userRepository.save(User.toEntity(signUpRequest, this.passwordEncoder));
        return SignUpResponse.from(savedUser);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String loginId) {
        return this.userRepository.findOneWithAuthoritiesByLoginId(loginId)
                .map(user -> createUser(loginId, user))
                .orElseThrow(() -> new UsernameNotFoundException(loginId + EUserServiceImpl.eUsernameNotFoundExceptionMessage.getValue()));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) throw new UserNotActivatedException();
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
        TokenInfoResponse tokenInfoResponse = validateLogin(loginRequest);
        updateTargetToken(user, loginRequest.getTargetToken());
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    public void updateTargetToken(User user, String targetToken) {
        user.setTargetToken(targetToken);
    }

    public User getUserByLoginId(String loginId) {
        return this.userRepository.findUserByLoginId(loginId).orElseThrow(NotFoundLoginIdException::new);
    }

    public UserInfoResponse getUserInfoByUserId(Long userId) {
        User user = validateUserId(userId);
        return UserInfoResponse.from(user);
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        validateUserId(userId);
        this.userRepository.deleteById(userId);
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = validateUserId(updateUserRequest.getUserId());
        updateUserRequest.setPassword(this.passwordEncoder.encode(updateUserRequest.getPassword()));
        User updatedUser = this.userRepository.save(new User());
        return UpdateUserResponse.from(updatedUser);
    }

    public User getUserByUserId(Long userId) {
        return validateUserId(userId);
    }

    public ReissueResponse reissueToken(ReissueRequest reissue){
        if (!this.tokenProvider.validateRefreshToken(reissue.getRefreshToken())) {
            throw new NotValidRefreshTokenException();}
        Authentication authentication = this.tokenProvider.getAuthentication(reissue.getAccessToken());
        String refreshToken = (String) this.redisTemplate.opsForValue().get(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName());
        if(ObjectUtils.isEmpty(refreshToken))
            throw new WrongRefreshTokenRequestException();
        if(!refreshToken.equals(reissue.getRefreshToken()))
            throw new NotMatchRefreshTokenException();
        TokenInfoResponse tokenInfoResponse = this.tokenProvider.createToken(authentication);
        this.redisTemplate.opsForValue()
                .set(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName(),
                        tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return ReissueResponse.from(tokenInfoResponse.getGrantType(), tokenInfoResponse.getAccessToken(),
                tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime());
    }

    @Transactional
    public DefaultResponse logout(LogoutRequest logoutRequest){
        if (!this.tokenProvider.validateToken(logoutRequest.getAccessToken()))
            throw new NotValidAccessTokenException();
        Authentication authentication = this.tokenProvider.getAuthentication(logoutRequest.getAccessToken());
        if (this.redisTemplate.opsForValue().get(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName()) != null)
            this.redisTemplate.delete(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName());
        Long expiration = tokenProvider.getExpiration(logoutRequest.getAccessToken());
        this.redisTemplate.opsForValue()
                .set(logoutRequest.getAccessToken(), EUserServiceImpl.eLogout.getValue(), expiration, TimeUnit.MILLISECONDS);
        deleteTargetToken(this.userRepository.findUserByLoginId(authentication.getName()).get());
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
            fileURL = EUserServiceImpl.eBaseFileURL.getValue() + this.myFileService.saveImage(file).getFileKey();
        } else fileURL = EUserServiceImpl.eBasePicture.getValue();
        user.setPicture(fileURL);
        return UpdateProfileResponse.from(fileURL);
    }

    @Override
    public FindLoginIdResponse findLoginId(FindLoginIdRequest findLoginIdRequest) {
        return FindLoginIdResponse.from(this.userRepository.findByEmailAndLoginType(findLoginIdRequest.getEmail(), ELoginType.eApp)
                .orElseThrow(NotFoundRegisteredUserByEmailExceptionMessage::new));
    }

    @Override
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        this.validateSamePassword(resetPasswordRequest);
        User user = this.getUserByUserId(resetPasswordRequest.getUserId());
        this.validateEmailAuthForResetPassword(user);
        user.setPassword(this.passwordEncoder.encode(resetPasswordRequest.getPassword()));
        user.getEmailAuth().setPasswordCheck(false);
        return ResetPasswordResponse.from(user);
    }


    /**
     * validate
     */


    public User validateUserId(Long userId){
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException());
    }

    public TokenInfoResponse validateLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
        Authentication authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenInfoResponse tokenInfoResponse = this.tokenProvider.createToken(authentication);
        this.redisTemplate.opsForValue()
                .set(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName(),
                        tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return tokenInfoResponse;
    }

    public User validateNotRegisteredEmail(String email) {
        return this.userRepository.findByEmailAndLoginType(email, ELoginType.eApp)
                .orElseThrow(() -> new NotFoundRegisteredUserByEmailExceptionMessage());
    }

    public void validateAlreadyRegisteredEmail(String email) {
        this.userRepository.findByEmailAndLoginType(email, ELoginType.eApp)
                .ifPresent((u -> { throw new AlreadyRegisteredEmailException(); }));
    }

    public void validateAlreadyRegisteredUser(String loginId) {
        this.userRepository.findOneWithAuthoritiesByLoginId(loginId).ifPresent((u -> {
                throw new AlreadyRegisteredUserException(); }));
    }

    public void validateIsAlreadyRegisteredUser(User user) {
        if(user.isEmailAuthActivated()) throw new AlreadyRegisteredUserException();
    }

    @Override
    public DefaultResponse validateLoginId(String loginId) {
        this.userRepository.findUserByLoginId(loginId)
                .ifPresent((u -> { throw new AlreadyRegisteredUserException(); }));
        return DefaultResponse.from(EUserServiceImpl.eNotDuplicatedLoginIdMessage.getValue());
    }

    private void validateSamePassword(ResetPasswordRequest resetPasswordRequest) {
        if(!resetPasswordRequest.getPassword().equals(resetPasswordRequest.getCheckPassword())){
            throw new NotSamePasswordException();}
    }

    private void validateEmailAuthForResetPassword(User user) {
        if (user.getEmailAuth()==null) throw new NotSendEmailException();
        else if(!user.getEmailAuth().isPasswordCheck()) throw new NotAuthenticationForChangePasswordException();}

}