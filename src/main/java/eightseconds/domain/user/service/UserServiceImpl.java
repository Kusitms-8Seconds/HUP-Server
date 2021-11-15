package eightseconds.domain.user.service;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.dto.LoginRequest;
import eightseconds.domain.user.dto.SignUpRequest;
import eightseconds.domain.user.entity.Authority;
import eightseconds.domain.user.entity.User;
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
    public User saveUser(SignUpRequest signUpRequest) {
        if (userRepository.findOneWithAuthoritiesByLoginId(signUpRequest.getLoginId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword())) // 개인정보 보호로, 비밀번호는 단방향 암호화
                .phoneNumber(signUpRequest.getPhoneNumber())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .loginType(UserConstants.ELoginType.eApp)
                .build();
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String loginId) {
        return userRepository.findOneWithAuthoritiesByLoginId(loginId)
                .map(user -> createUser(loginId, user))
                .orElseThrow(() -> new UsernameNotFoundException(loginId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }
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

    public String validationLogin(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return jwt;
    }

    public User getUserByLoginId(String loginId) {
        Optional<User> user = userRepository.findUserByLoginId(loginId);
        return user.get();
    }

    public User getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }

    /**
     * validation
     */

    public boolean validationUserId(Long userId){
        Optional<User> user = userRepository.findUserById(userId);
        if(!user.isEmpty()){
            return true;
        } else throw new IllegalArgumentException("해당 유저아이디로 유저를 찾을 수 없습니다.");
    }
}