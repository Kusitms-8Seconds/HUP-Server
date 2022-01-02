package eightseconds.domain.user.controller;

import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserServiceImpl;
import eightseconds.global.jwt.JwtFilter;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class UserApiController {

    private final UserServiceImpl userService;

    @ApiOperation(value = "사용자 생성", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = userService.saveUser(signUpRequest);
        URI createdUri = linkTo(UserApiController.class).slash(signUpResponse).toUri();
        return ResponseEntity.created(createdUri).body(signUpResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = userService.validationLogin(loginRequest);
        User user = userService.getUserByLoginId(loginRequest.getLoginId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponse(jwt, user.getId()), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/tokenTest")
    public String test() {
        return "Complete Token Test!!";
    }

    @PostMapping("/user/details")
    public ResponseEntity<UserDetailsInfoResponse> getUserDetails(@Valid @RequestBody UserDetailsInfoRequest userDetailsInfoRequest) {
        User user = userService.getUserByUserId(userDetailsInfoRequest.getUserId());
        return ResponseEntity.ok(UserDetailsInfoResponse.from(user));
    }

}
