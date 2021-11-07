package eightseconds.domain.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.InvalidIdToken;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.config.dto.OAuth2Attribute;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService{

    private String Client_ID = "940555005991-aptm7147s5n4ef0og6dj366ll2vffjaq.apps.googleusercontent.com";
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private static String KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com";
    private static String KAKAO_API_BASE_URL = "https://kapi.kakao.com";
    private static String APP_KEY = "e4b092a0692591def92fb184787c6d56";
    private static String REDIRECT_URL = "https://localhost:8080/oauth";

    public GoogleIdToken validationGoogleIdToken(OAuth2GoogleLoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException {
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
        saveOrUpdateGoogle(payload);
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

    private User saveOrUpdateGoogle(Payload payload) {
        User user = userRepository.findByEmail(payload.getEmail())
                .map(entity -> entity.update((String) payload.get("name"), (String) payload.get("picture")))
                .orElse(User.toEntityOfGoogleUser(payload));
        return userRepository.save(user);
    }

    @Override
    public String validationKakaoCode(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        HashMap<String, Object> userInfo = getUserInfo(oAuth2KakaoLoginRequest.getAccessToken());
        saveOrUpdateKakao(userInfo);
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of("kakao", "kakao", userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, "key");
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "key");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(auth);
        return jwt;
    }

    private User saveOrUpdateKakao(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmail(userInfo.get("email").toString())
                .map(entity -> entity.update(userInfo.get("nickname").toString(), userInfo.get("profile_image").toString()))
                .orElse(User.toEntityOfKakaoUser(userInfo));
        return userRepository.save(user);
    }

    public HashMap<String, Object> getUserInfo (String access_Token) {

        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            userInfo.put("profile_image", profile_image);

            System.out.println("nickname" + nickname);
            System.out.println("email" + email);
            System.out.println("profile_image" + profile_image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userInfo;
    }
}
