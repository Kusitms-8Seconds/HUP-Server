package eightseconds.infra.email.controller;

import eightseconds.global.dto.DefaultResponse;
import eightseconds.infra.email.dto.CheckAuthCodeRequest;
import eightseconds.infra.email.dto.EmailAuthCodeRequest;
import eightseconds.infra.email.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    @ApiOperation(value = "회원 가입시 이메일 인증", notes = "기존사용하고 있는 이메일을 통해 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> sendEmailAuthCode(
            @Valid @RequestBody @ApiParam(value="이메일정보 정보", required = true) EmailAuthCodeRequest emailAuthCodeRequest) throws Exception {
        DefaultResponse defaultResponse = emailService.sendSimpleMessage(emailAuthCodeRequest.getEmail());
        return ResponseEntity.status(200).body(defaultResponse);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "인증코드 검증", notes = "해당 유저의 Email로 보낸 인증코드와 입력한 인증코드가 맞는지 여부 검사")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> verifyAuthCode(
            @Valid @RequestBody @ApiParam(value="인증 코드", required = true) CheckAuthCodeRequest checkAuthCodeRequest){
        DefaultResponse defaultResponse = emailService.checkAuthCode(checkAuthCodeRequest.getAuthCode());
        return ResponseEntity.status(200).body(defaultResponse);
    }

}
