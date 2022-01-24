package eightseconds.domain.user.controller;

import com.nimbusds.jose.shaded.json.parser.ParseException;
import eightseconds.domain.user.constant.UserConstants.EOAuth2UserApiController;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.dto.OAuth2NaverLoginRequest;
import eightseconds.domain.user.service.OAuth2UserService;
import eightseconds.global.jwt.JwtFilter;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class OAuth2UserApiController {

    private final OAuth2UserService oAuth2UserService;

    @ApiOperation(value = "구글 로그인", notes = "구글 로그인을 합니다.")
    @PostMapping("/google-login")
    public ResponseEntity<EntityModel<LoginResponse>> googleLogin(@RequestBody OAuth2GoogleLoginRequest oAuth2GoogleLoginRequest) throws GeneralSecurityException, IOException {
        LoginResponse loginResponse = oAuth2UserService.validateGoogleIdToken(oAuth2GoogleLoginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, EOAuth2UserApiController.eBearerHeaderValue.getValue() + loginResponse.getToken());
        return new ResponseEntity<>(EntityModel.of(loginResponse)
                .add(linkTo(methodOn(this.getClass()).googleLogin(oAuth2GoogleLoginRequest)).withSelfRel())
                .add(linkTo(methodOn(UserApiController.class).getUser(loginResponse.getUserId())).withRel(EOAuth2UserApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(UserApiController.class).deleteUser(loginResponse.getUserId())).withRel(EOAuth2UserApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(UserApiController.class).updateUser(null))
                        .withRel(EOAuth2UserApiController.eUpdateMethod.getValue())), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인을 합니다.")
    @PostMapping("/kakao-login")
    public ResponseEntity<EntityModel<LoginResponse>> kakaoLogin(@RequestBody OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) throws IOException, ParseException, org.json.simple.parser.ParseException {
        LoginResponse loginResponse = oAuth2UserService.validateKakaoAccessToken(oAuth2KakaoLoginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, EOAuth2UserApiController.eBearerHeaderValue.getValue() + loginResponse.getToken());
        return new ResponseEntity<>(EntityModel.of(loginResponse)
                .add(linkTo(methodOn(this.getClass()).kakaoLogin(oAuth2KakaoLoginRequest)).withSelfRel())
                .add(linkTo(methodOn(UserApiController.class).getUser(loginResponse.getUserId())).withRel(EOAuth2UserApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(UserApiController.class).deleteUser(loginResponse.getUserId())).withRel(EOAuth2UserApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(UserApiController.class).updateUser(null))
                        .withRel(EOAuth2UserApiController.eUpdateMethod.getValue())), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인을 합니다.")
    @PostMapping("/naver-login")
    public ResponseEntity<EntityModel<LoginResponse>> naverLogin(@RequestBody OAuth2NaverLoginRequest oAuth2NaverLoginRequest){
        LoginResponse loginResponse = oAuth2UserService.validateNaverAccessToken(oAuth2NaverLoginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, EOAuth2UserApiController.eBearerHeaderValue.getValue() + loginResponse.getToken());
        return new ResponseEntity<>(EntityModel.of(loginResponse)
                .add(linkTo(methodOn(this.getClass()).naverLogin(oAuth2NaverLoginRequest)).withSelfRel())
                .add(linkTo(methodOn(UserApiController.class).getUser(loginResponse.getUserId())).withRel(EOAuth2UserApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(UserApiController.class).deleteUser(loginResponse.getUserId())).withRel(EOAuth2UserApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(UserApiController.class).updateUser(null))
                        .withRel(EOAuth2UserApiController.eUpdateMethod.getValue())), httpHeaders, HttpStatus.OK);
    }
}
