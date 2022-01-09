package eightseconds.domain.user.service;

import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.Authority;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.AlreadyRegisteredUserException;
import eightseconds.domain.user.exception.NotActivatedEmailAuthException;
import eightseconds.domain.user.exception.NotFoundUserException;
import eightseconds.domain.user.exception.UserNotActivatedException;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.jwt.TokenProvider;
import eightseconds.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
                .orElseThrow(() -> new UsernameNotFoundException(loginId + EUserServiceImpl.eUsernameNotFoundException.getValue()));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) throw new UserNotActivatedException(username + EUserServiceImpl.eUserNotActivatedException.getValue());

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

    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = getUserByLoginId(loginRequest.getLoginId());
        validateEmailAuth(user);
        String token = validateLogin(loginRequest);

        return LoginResponse.from(token, user.getId());
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
        User updatedUser = userRepository.save(user.updateUserByUpdateUserRequest(updateUserRequest));
        return UpdateUserResponse.from(updatedUser);
    }

    public User getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    /**
     * validate
     */

    public User validateUserId(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(!user.isEmpty()){ return user.get(); }
        else throw new NotFoundUserException(EUserServiceImpl.eNotFoundUserException.getValue());
    }

    public String validateLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return jwt;
    }

    private void validateEmailAuth(User user) {
        if (!user.isEmailAuthActivated()) throw new NotActivatedEmailAuthException(EUserServiceImpl.eNotActivatedEmailAuthException.getValue());
    }

}