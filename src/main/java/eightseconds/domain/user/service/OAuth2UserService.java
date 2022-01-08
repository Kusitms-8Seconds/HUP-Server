package eightseconds.domain.user.service;

import com.nimbusds.jose.shaded.json.parser.ParseException;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.dto.OAuth2NaverLoginRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface OAuth2UserService {
    LoginResponse validationGoogleIdToken(OAuth2GoogleLoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException;
    LoginResponse validationKakaoAccessToken(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) throws IOException, ParseException, org.json.simple.parser.ParseException;
    LoginResponse validationNaverAccessToken(OAuth2NaverLoginRequest oAuth2NaverLoginRequest);
}
