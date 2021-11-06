package eightseconds.domain.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import eightseconds.domain.user.dto.OAuth2LoginRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface OAuth2UserService {
    GoogleIdToken validationGoogleIdToken(OAuth2LoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException;
    String saveUserOrUpdateByGoogleIdToken(GoogleIdToken idToken);
}
