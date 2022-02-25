package eightseconds.domain.user.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.service.FileService;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.Authority;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.*;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.jwt.TokenProvider;
import eightseconds.global.util.SecurityUtil;
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
import java.util.*;
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
        if (userRepository.findOneWithAuthoritiesByLoginId(signUpRequest.getLoginId()).orElse(null) != null) {
                throw new AlreadyRegisteredUserException(EUserServiceImpl.eAlreadyRegisteredUserExceptionMessage.getValue()); }

        Authority authority = Authority.builder()
                .authorityName(EUserServiceImpl.eAuthorityRoleUser.getValue())
                .build();
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword())) // 개인정보 보호로, 비밀번호는 단방향 암호화
                .phoneNumber(signUpRequest.getPhoneNumber())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .picture(EUserServiceImpl.eBasePicture.getValue())
                .loginType(ELoginType.eApp)
                .build();

        User savedUser = this.userRepository.save(user);

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

    @Transactional()
    public Optional<User> getUserWithAuthorities(String loginId) { // loginId을 기준으로 정보를 가져옴
        return userRepository.findOneWithAuthoritiesByLoginId(loginId);
    }

    @Transactional()
    public Optional<User> getMyUserWithAuthorities() { // SecurityContext에 저장된 loginId의 정보만 가져온다.
        return SecurityUtil.getCurrentLoginId().flatMap(userRepository::findOneWithAuthoritiesByLoginId);
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = getUserByLoginId(loginRequest.getLoginId());
        validateEmailAuth(user);
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

    public ReissueResponse reissueToken(ReissueRequest reissue) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(reissue.getRefreshToken())) {
            throw new NotValidRefreshTokenException(EUserServiceImpl.eNotValidRefreshTokenExceptionMessage.getValue());}

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            throw new WrongRefreshTokenRequestException(EUserServiceImpl.eWrongRefreshTokenRequestExceptionMessage.getValue());
        }
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            throw new NotMatchRefreshTokenException(EUserServiceImpl.eNotMatchRefreshTokenExceptionMessage.getValue());
        }

        // 4. 새로운 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName(),
                        tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return ReissueResponse.from(tokenInfoResponse.getGrantType(), tokenInfoResponse.getAccessToken(),
                tokenInfoResponse.getRefreshToken(), tokenInfoResponse.getRefreshTokenExpirationTime());
    }

    @Transactional
    public DefaultResponse logout(LogoutRequest logoutRequest) {
        // 1. Access Token 검증
        if (!tokenProvider.validateToken(logoutRequest.getAccessToken())) {
            throw new NotValidAccessTokenException(EUserServiceImpl.eNotValidAccessTokenExceptionMessage.getValue());
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(logoutRequest.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete(EUserServiceImpl.eRefreshToken.getValue() + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
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
            fileURL = makeFileURL(myFile.getFilename());
        } else {
            fileURL = EUserServiceImpl.eBasePicture.getValue();
        }
        user.setPicture(fileURL);
        return UpdateProfileResponse.from(fileURL);
    }

    private String makeFileURL(String filename) {
        return EUserServiceImpl.eBaseFileURL.getValue()+filename;
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

    private void validateEmailAuth(User user) {
        if (!user.isEmailAuthActivated()) throw new NotActivatedEmailAuthException(EUserServiceImpl.eNotActivatedEmailAuthExceptionMessage.getValue());
    }

    public User validateEmail(String email) {
        return userRepository.findByEmailAndLoginType(email, ELoginType.eApp)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundRegisteredUserException(EUserServiceImpl
                        .eNotFoundRegisteredUserExceptionMessage.getValue()));

    }

    public void validateIsAlreadyRegisteredUser(String email) {
        userRepository.findByEmailAndLoginTypeAndEmailAuthActivated(email, ELoginType.eApp, true)
                .ifPresent(u -> { throw new AlreadyRegisteredUserException(EUserServiceImpl
                        .eAlreadyRegisteredUserExceptionMessage.getValue()); });
    }

    @Override
    public DefaultResponse validateLoginId(String loginId) {
        userRepository.findUserByLoginId(loginId)
                .stream()
                .findAny()
                .ifPresent((u -> { throw new AlreadyRegisteredUserException(EUserServiceImpl
                        .eAlreadyRegisteredLoginIdExceptionMessage.getValue()); }));
        return DefaultResponse.from(EUserServiceImpl.eNotDuplicatedLoginIdMessage.getValue());
    }

}