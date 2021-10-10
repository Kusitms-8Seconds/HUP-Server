package kusitms.domain.user.controller;

import kusitms.domain.user.constant.UserConstants;
import kusitms.domain.user.dto.SignUpRequest;
import kusitms.domain.user.dto.SignUpResponse;
import kusitms.domain.user.entity.User;
import kusitms.domain.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserServiceImpl userService;

    @PostMapping("signup")
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        User saved = userService.saveUser(signUpRequest);
        return ResponseEntity.ok(SignUpResponse.of(saved.getEmail(), UserConstants.SUCCESS_SIGN_UP.getMessage()));
    }

}
