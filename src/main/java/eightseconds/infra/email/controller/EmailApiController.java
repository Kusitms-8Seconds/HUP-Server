package eightseconds.infra.email.controller;

import eightseconds.global.dto.DefaultResponse;
import eightseconds.infra.email.dto.CheckAuthCodeRequest;
import eightseconds.infra.email.dto.EmailAuthCodeRequest;
import eightseconds.infra.email.dto.EmailResetPasswordResponse;
import eightseconds.infra.email.service.EmailService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/email")
@Api(tags = "Email API")
public class EmailApiController {

    private final EmailService emailService;

    @ApiOperation(value = "회원 가입시 이메일 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
    @PostMapping("/send/activate")
    public ResponseEntity<EntityModel<DefaultResponse>> sendSimpleMessageForActivateUser(
            @Valid @RequestBody @ApiParam(value="이메일정보", required = true) EmailAuthCodeRequest emailAuthCodeRequest) throws Exception {
        DefaultResponse defaultResponse = emailService.sendSimpleMessageForActivateUser(emailAuthCodeRequest.getEmail());
        return ResponseEntity.ok((EntityModel.of(defaultResponse)
                .add(linkTo(methodOn(this.getClass()).sendSimpleMessageForActivateUser(emailAuthCodeRequest)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).verifyAuthCode(null)).withRel("get"))));
    }

    @ApiOperation(value = "비밀번호 재설정을 위한 이메일 인증", notes = "비밀번호 재설정을 위한 이메일 인증")
    @PostMapping("/send/reset")
    public ResponseEntity<EntityModel<DefaultResponse>> sendSimpleMessageForResetPassword(
            @Valid @RequestBody @ApiParam(value="이메일정보", required = true) EmailAuthCodeRequest emailAuthCodeRequest) throws Exception {
        DefaultResponse defaultResponse = emailService.sendSimpleMessageForResetPassword(emailAuthCodeRequest.getEmail());
        return ResponseEntity.ok((EntityModel.of(defaultResponse)
                .add(linkTo(methodOn(this.getClass()).sendSimpleMessageForResetPassword(emailAuthCodeRequest)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).verifyAuthCode(null)).withRel("get"))));
    }

    @PostMapping("/activate-user")
    @ApiOperation(value = "유저 활성화 하기 위한 인증코드 검증", notes = "해당 유저의 Email로 보낸 인증코드와 입력한 인증코드가 맞는지 여부 검사")
    public ResponseEntity<?> verifyActivateUser(
            @Valid @RequestBody @ApiParam(value="인증 코드", required = true) CheckAuthCodeRequest checkAuthCodeRequest){
        DefaultResponse defaultResponse = emailService.activateUserAuthCode(checkAuthCodeRequest.getAuthCode());
        return ResponseEntity.ok((EntityModel.of(defaultResponse)
                .add(linkTo(methodOn(this.getClass()).verifyAuthCode(checkAuthCodeRequest)).withSelfRel())));
    }

    @PostMapping("/reset-password")
    @ApiOperation(value = "비밀번호 재설정을 위한 인증코드 검증", notes = "해당 유저의 Email로 보낸 인증코드와 입력한 인증코드가 맞는지 여부 검사")
    public ResponseEntity<?> verifyAuthCode(
            @Valid @RequestBody @ApiParam(value="인증 코드", required = true) CheckAuthCodeRequest checkAuthCodeRequest){
        EmailResetPasswordResponse emailResetPasswordResponse = emailService.resetPasswordAuthCode(checkAuthCodeRequest.getAuthCode());
        return ResponseEntity.ok((EntityModel.of(emailResetPasswordResponse)
                .add(linkTo(methodOn(this.getClass()).verifyAuthCode(checkAuthCodeRequest)).withSelfRel())));
    }

}
