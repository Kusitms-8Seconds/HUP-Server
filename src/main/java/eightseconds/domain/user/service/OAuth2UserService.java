package eightseconds.domain.user.service;

import com.nimbusds.jose.shaded.json.parser.ParseException;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.dto.OAuth2NaverLoginRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface OAuth2UserService {
    LoginResponse validateGoogleIdToken(OAuth2GoogleLoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException;
    LoginResponse validateKakaoAccessToken(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) throws IOException, ParseException, org.json.simple.parser.ParseException;
    LoginResponse validateNaverAccessToken(OAuth2NaverLoginRequest oAuth2NaverLoginRequest);
}
