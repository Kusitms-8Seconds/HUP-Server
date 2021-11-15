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
import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.dto.LoginResponse;
import eightseconds.domain.user.dto.OAuth2GoogleLoginRequest;
import eightseconds.domain.user.dto.OAuth2KakaoLoginRequest;
import eightseconds.domain.user.dto.OAuth2NaverLoginRequest;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.InvalidIdToken;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.OAuth2Attribute;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService{

    private String Client_ID = "221537301769-e1qd8130nulhheiqo68nv8upistikcp4.apps.googleusercontent.com";
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private static String KAKAO_AUTH_BASE_URL = "https://kauth.kakao.com";
    private static String KAKAO_API_BASE_URL = "https://kapi.kakao.com";
    private static String APP_KEY = "6605274d9a8165288410480f4bd1fa9b";
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

    public LoginResponse saveUserOrUpdateByGoogleIdToken(GoogleIdToken idToken) {
        Payload payload = idToken.getPayload();
        User user = saveOrUpdateGoogle(payload);
        String jwt = makeAppToken(payload);
        return LoginResponse.from(jwt, user.getId());
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
        User user = userRepository.findByEmailAndLoginType(payload.getEmail(), UserConstants.ELoginType.eGoogle)
                .map(entity -> entity.update((String) payload.get("name"), (String) payload.get("picture")))
                .orElse(User.toEntityOfGoogleUser(payload));
        return userRepository.save(user);
    }

    @Override
    public LoginResponse validationKakaoAccessToken(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        HashMap<String, Object> userInfo = getUserInfo(oAuth2KakaoLoginRequest.getAccessToken());
        User user = saveOrUpdateKakao(userInfo);
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of("kakao", "kakao", userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, "key");
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "key");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(auth);
        return LoginResponse.from(jwt, user.getId());
    }

    private User saveOrUpdateKakao(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmailAndLoginType(userInfo.get("email").toString(), UserConstants.ELoginType.eKakao)
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
            if(kakao_account!= null){
                String email = kakao_account.getAsJsonObject().get("email").getAsString();
                userInfo.put("email", email);
                System.out.println("email" + email);
            }

            userInfo.put("nickname", nickname);
            userInfo.put("profile_image", profile_image);

            System.out.println("nickname" + nickname);
            System.out.println("profile_image" + profile_image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userInfo;
    }


    public LoginResponse validationNaverAccessToken(OAuth2NaverLoginRequest oAuth2NaverLoginRequest) {

        String header = "Bearer " + oAuth2NaverLoginRequest.getAccessToken(); // Bearer 다음에 공백 추가
        String apiURL = "https://openapi.naver.com/v1/nid/me";
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);
        HashMap<String, Object> userInfo = getNaverUserInfo(responseBody);

        User user = saveOrUpdateNaver(userInfo);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of("naver", "naver", userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, "key");
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, "key");
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(auth);
        return LoginResponse.from(jwt, user.getId());

    }

    private User saveOrUpdateNaver(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmailAndLoginType(userInfo.get("email").toString(), UserConstants.ELoginType.eNaver)
                .map(entity -> entity.update(userInfo.get("name").toString(), userInfo.get("profile_image").toString()))
                .orElse(User.toEntityOfNaverUser(userInfo));
        return userRepository.save(user);
    }

    private HashMap<String, Object> getNaverUserInfo(String responseBody) {
        HashMap<String, Object> userInfo = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(responseBody);

        JsonObject jsonContent = element.getAsJsonObject().get("response").getAsJsonObject();
        System.out.println("element" + element.toString());

        String nickname = jsonContent.get("name").getAsString();
        String profile_image = jsonContent.get("profile_image").getAsString();
        String email = jsonContent.get("email").getAsString();

        userInfo.put("name", nickname);
        userInfo.put("email", email);
        userInfo.put("profile_image", profile_image);
        return userInfo;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
