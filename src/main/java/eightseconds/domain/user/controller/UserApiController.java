package eightseconds.domain.user.controller;

import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserServiceImpl;
import eightseconds.global.jwt.JwtFilter;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserApiController {

    private final UserServiceImpl userService;

    @ApiOperation(value = "사용자 생성", notes = "회원가입을 합니다.")
    @PostMapping
    public ResponseEntity<SignUpResponse> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = userService.saveUser(signUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(signUpResponse.getUserId())
                .toUri();
        return ResponseEntity.created(location).body(signUpResponse);
    }

    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 정보 조회", notes = "사용자의 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfoByUserId(id));
    }

    @ApiOperation(value = "사용자 정보 삭제", notes = "사용자의 정보를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<?>> deleteUser(@PathVariable Long id) {
        userService.deleteUserByUserId(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "사용자의 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<UpdateUserResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(updateUserResponse);
    }

    @GetMapping("/tokenTest")
    public String test() {
        return "Complete Token Test!!";
    }

}
