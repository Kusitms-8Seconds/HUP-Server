package eightseconds.domain.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import eightseconds.domain.user.dto.OAuth2LoginRequest;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.InvalidIdToken;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.config.dto.OAuth2Attribute;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService{

    private String Client_ID = "940555005991-aptm7147s5n4ef0og6dj366ll2vffjaq.apps.googleusercontent.com";
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public GoogleIdToken validationGoogleIdToken(OAuth2LoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(Client_ID))
                .setIssuer("https://accounts.google.com")
                .build();
        GoogleIdToken idToken = verifier.verify(oAuth2LoginRequest.getIdToken());
        if(idToken != null){ return idToken; }
        else { throw new InvalidIdToken("Invalid ID token."); }
    }

    public String saveUserOrUpdateByGoogleIdToken(GoogleIdToken idToken) {
        Payload payload = idToken.getPayload();
        saveOrUpdate(payload);
        String jwt = makeAppToken(payload);
        return jwt;
    }

    private String makeAppToken(Payload payload) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Map<String, Object> map = new HashMap<>();
        map.put("id", "sub");
        map.put("key", "sub");
        map.put("name", (String) payload.get("name"));
        map.put("email", (String) payload.getEmail());
        map.put("picture", (String) payload.get("picture"));
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of("google", "sub", map);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, "key");
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "key");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(auth);
        return jwt;
    }

    private User saveOrUpdate(Payload payload) {
        User user = userRepository.findByEmail(payload.getEmail())
                .map(entity -> entity.update((String) payload.get("name"), (String) payload.get("picture")))
                .orElse(User.toEntity(payload));
        return userRepository.save(user);
    }
}
