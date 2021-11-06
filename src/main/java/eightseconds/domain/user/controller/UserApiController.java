package eightseconds.domain.user.controller;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.dto.LoginRequest;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.SignUpRequest;
import eightseconds.domain.user.dto.SignUpResponse;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.OAuth2UserService;
import eightseconds.domain.user.service.UserServiceImpl;
import eightseconds.global.jwt.JwtFilter;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        User saved = userService.saveUser(signUpRequest);
        return ResponseEntity.ok(SignUpResponse.from(saved.getLoginId(), UserConstants.SUCCESS_SIGN_UP.getMessage()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = userService.validationLogin(loginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponse(jwt), httpHeaders, HttpStatus.OK);
    }

//    @GetMapping("/user")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public ResponseEntity<User> getMyUserInfo() {
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
//    }
//
//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
//    }

    @GetMapping("/api/tokenTest")
    public String test() {
        return "Complete Token Test!!";
    }

}
