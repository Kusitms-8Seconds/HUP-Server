package eightseconds.domain.user.controller;

import eightseconds.domain.user.constant.UserConstants.EUserApiController;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.service.UserServiceImpl;
import eightseconds.global.dto.DefaultResponse;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserApiController {

    private final UserServiceImpl userService;

    @ApiOperation(value = "사용자 생성", notes = "회원가입을 합니다.")
    @PostMapping
    public ResponseEntity<EntityModel<SignUpResponse>> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = userService.saveUser(signUpRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(EUserApiController.eLocationIdPath.getValue())
                .buildAndExpand(signUpResponse.getUserId())
                .toUri();

        return ResponseEntity.created(location).body(EntityModel.of(signUpResponse)
                .add(linkTo(methodOn(this.getClass()).createUser(signUpRequest)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getUser(signUpResponse.getUserId())).withRel(EUserApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).deleteUser(signUpResponse.getUserId())).withRel(EUserApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).updateUser(null)).withRel(EUserApiController.eUpdateMethod.getValue())));
    }

    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<EntityModel<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());

        return new ResponseEntity<>(EntityModel.of(loginResponse)
                .add(linkTo(methodOn(this.getClass()).loginUser(loginRequest)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getUser(loginResponse.getUserId())).withRel(EUserApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).deleteUser(loginResponse.getUserId())).withRel(EUserApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).updateUser(null)).withRel(EUserApiController.eUpdateMethod.getValue())), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 정보 조회", notes = "사용자의 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserInfoResponse>> getUser(@PathVariable Long id) {

        return ResponseEntity.ok((EntityModel.of(userService.getUserInfoByUserId(id))
                .add(linkTo(methodOn(this.getClass()).getUser(id)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).deleteUser(id)).withRel(EUserApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).updateUser(null)).withRel(EUserApiController.eUpdateMethod.getValue()))));
    }

    @ApiOperation(value = "사용자 정보 삭제", notes = "사용자의 정보를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel<?>> deleteUser(@PathVariable Long id) {
        userService.deleteUserByUserId(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "사용자의 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<EntityModel<UpdateUserResponse>> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse updateUserResponse = userService.updateUser(updateUserRequest);

        return ResponseEntity.ok((EntityModel.of(updateUserResponse)
                .add(linkTo(methodOn(this.getClass()).updateUser(updateUserRequest)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getUser(updateUserResponse.getUserId())).withRel(EUserApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).deleteUser(updateUserResponse.getUserId())).withRel(EUserApiController.eDeleteMethod.getValue()))));
    }

    @ApiOperation(value = "아이디 중복 체크", notes = "아이디가 중복인지 체크합니다.")
    @GetMapping("/check/{loginId}")
    public ResponseEntity<EntityModel<DefaultResponse>> getIsRegisteredLoginId(@PathVariable String loginId) {
        return ResponseEntity.ok((EntityModel.of(userService.validateLoginId(loginId)))
                .add(linkTo(methodOn(this.getClass()).getIsRegisteredLoginId(loginId)).withSelfRel()));
    }
}
