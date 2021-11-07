package eightseconds.domain.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.dto.OAuth2NaverLoginRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;

public interface OAuth2UserService {
    GoogleIdToken validationGoogleIdToken(OAuth2GoogleLoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException;
    LoginResponse saveUserOrUpdateByGoogleIdToken(GoogleIdToken idToken);
    LoginResponse validationKakaoAccessToken(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) throws IOException, ParseException, org.json.simple.parser.ParseException;
    LoginResponse validationNaverAccessToken(OAuth2NaverLoginRequest oAuth2NaverLoginRequest);
}
