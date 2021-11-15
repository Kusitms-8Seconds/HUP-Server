package eightseconds.domain.user.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.dto.OAuth2NaverLoginRequest;
import eightseconds.domain.user.service.OAuth2UserService;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@RestController
public class OAuth2UserApiController {

    private final OAuth2UserService oAuth2UserService;
    private final UserService userService;

    @PostMapping("oauth2/google/validation")
    public ResponseEntity<LoginResponse> googleIdTokenValidation(@RequestBody OAuth2GoogleLoginRequest oAuth2GoogleLoginRequest) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = oAuth2UserService.validationGoogleIdToken(oAuth2GoogleLoginRequest);
        LoginResponse loginResponse = oAuth2UserService.saveUserOrUpdateByGoogleIdToken(idToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("oauth2/kakao/validation")
    public ResponseEntity<LoginResponse> kakaoAccessTokenValidation(@RequestBody OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) throws IOException, ParseException, org.json.simple.parser.ParseException {

        LoginResponse loginResponse = oAuth2UserService.validationKakaoAccessToken(oAuth2KakaoLoginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("oauth2/naver/validation")
    public ResponseEntity<LoginResponse> naverAccessTokenValidation(@RequestBody OAuth2NaverLoginRequest oAuth2NaverLoginRequest) throws IOException, ParseException, org.json.simple.parser.ParseException {

        LoginResponse loginResponse = oAuth2UserService.validationNaverAccessToken(oAuth2NaverLoginRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
    }
}
