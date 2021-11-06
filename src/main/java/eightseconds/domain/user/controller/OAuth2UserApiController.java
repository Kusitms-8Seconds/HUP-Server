package eightseconds.domain.user.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2LoginRequest;
import eightseconds.domain.user.service.OAuth2UserService;
import eightseconds.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@RestController
public class OAuth2UserApiController {

    private final OAuth2UserService oAuth2UserService;

    @GetMapping("oauth2/google/validation")
    public ResponseEntity<LoginResponse> googleIdTokenValidation(@RequestBody OAuth2LoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = oAuth2UserService.validationGoogleIdToken(oAuth2LoginRequest);
        String jwt = oAuth2UserService.saveUserOrUpdateByGoogleIdToken(idToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponse(jwt), httpHeaders, HttpStatus.OK);
    }
}
