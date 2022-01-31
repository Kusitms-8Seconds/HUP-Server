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
import eightseconds.domain.user.constant.UserConstants.EOAuth2UserServiceImpl;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.InvalidIdToken;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.OAuth2Attribute;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:application-oauth.properties")
public class OAuth2UserServiceImpl implements OAuth2UserService{

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.provider.google.issuer-uri}")
    private String googleIssuer;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUrl;

    public LoginResponse validateGoogleIdToken(OAuth2GoogleLoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .setIssuer(googleIssuer)
                .build();
        GoogleIdToken idToken = verifier.verify(oAuth2LoginRequest.getIdToken());
        if(idToken != null) return saveUserOrUpdateByGoogleIdToken(idToken);
        else throw new InvalidIdToken(EOAuth2UserServiceImpl.eGoogleInvalidIdTokenMessage.getValue());
    }

    public LoginResponse saveUserOrUpdateByGoogleIdToken(GoogleIdToken idToken) {
        Payload payload = idToken.getPayload();
        User user = saveOrUpdateGoogleUser(payload);
        TokenInfoResponse tokenInfoResponse = makeAppToken(payload);
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    private User saveOrUpdateGoogleUser(Payload payload) {
        User user = userRepository.findByEmailAndLoginType(payload.getEmail(), ELoginType.eGoogle)
                .map(entity -> entity.update((String) payload.get(EOAuth2UserServiceImpl.eGoogleNameAttribute.getValue()),
                        (String) payload.get(EOAuth2UserServiceImpl.eGooglePictureAttribute.getValue())))
                .orElse(User.toEntityOfGoogleUser(payload));
        return userRepository.save(user);
    }

    private TokenInfoResponse makeAppToken(Payload payload) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(EOAuth2UserServiceImpl.eRoleUser.getValue()));
        Map<String, Object> map = new HashMap<>();
        map.put(EOAuth2UserServiceImpl.eGoogleIdAttribute.getValue(), EOAuth2UserServiceImpl.eGoogleSub.getValue());
        map.put(EOAuth2UserServiceImpl.eGoogleKeyAttribute.getValue(), EOAuth2UserServiceImpl.eGoogleSub.getValue());
        map.put(EOAuth2UserServiceImpl.eGoogleNameAttribute.getValue(), payload.get(EOAuth2UserServiceImpl.eGoogleNameAttribute.getValue()));
        map.put(EOAuth2UserServiceImpl.eGoogleEmailAttribute.getValue(), payload.getEmail());
        map.put(EOAuth2UserServiceImpl.eGooglePictureAttribute.getValue(), payload.get(EOAuth2UserServiceImpl.eGooglePictureAttribute.getValue()));

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(EOAuth2UserServiceImpl.eGoogle.getValue(), EOAuth2UserServiceImpl.eGoogleSub.getValue(), map);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, EOAuth2UserServiceImpl.eGoogleKeyAttribute.getValue());
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, EOAuth2UserServiceImpl.eGoogleKeyAttribute.getValue());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);
        return tokenInfoResponse;
    }

    @Override
    public LoginResponse validateKakaoAccessToken(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(EOAuth2UserServiceImpl.eRoleUser.getValue()));
        HashMap<String, Object> userInfo = getUserInfo(oAuth2KakaoLoginRequest.getAccessToken());
        User user = saveOrUpdateKakao(userInfo);
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(EOAuth2UserServiceImpl.eKakao.getValue(), EOAuth2UserServiceImpl.eKakao.getValue(), userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, EOAuth2UserServiceImpl.eKakaoKeyAttribute.getValue());
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, EOAuth2UserServiceImpl.eKakaoKeyAttribute.getValue());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    public HashMap<String, Object> getUserInfo(String access_Token) {

        HashMap<String, Object> userInfo = new HashMap<>();
        try {
            URL url = new URL(kakaoUserInfoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(EOAuth2UserServiceImpl.eKakaoGetMethod.getValue());
            conn.setRequestProperty(EOAuth2UserServiceImpl.eKakaoAuthorization.getValue(), EOAuth2UserServiceImpl.eKakaoBearer.getValue() + access_Token);
            conn.setRequestProperty(EOAuth2UserServiceImpl.eKakaoContentType.getValue(), EOAuth2UserServiceImpl.eKakaoContentTypeUrlencoded.getValue());

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            String result = EOAuth2UserServiceImpl.eKakaoEmpty.getValue();

            while ((line = br.readLine()) != null) {
                result += line;}

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject kakao_account = element.getAsJsonObject().get(EOAuth2UserServiceImpl.eKakaoAccountAttribute.getValue()).getAsJsonObject();

            if(kakao_account != null){
                JsonObject profile = kakao_account.getAsJsonObject().get(EOAuth2UserServiceImpl.eKakaoProfile.getValue()).getAsJsonObject();
                String nickname = profile.getAsJsonObject().get(EOAuth2UserServiceImpl.eKakaoNickNameAttribute.getValue()).getAsString();
                String profile_image = profile.getAsJsonObject().get(EOAuth2UserServiceImpl.eKakaoProfileImageAttribute.getValue()).getAsString();
                String email = kakao_account.getAsJsonObject().get(EOAuth2UserServiceImpl.eKakaoEmailAttribute.getValue()).getAsString();
                userInfo.put(EOAuth2UserServiceImpl.eKakaoNickNameAttribute.getValue(), nickname);
                userInfo.put(EOAuth2UserServiceImpl.eKakaoProfileImageAttribute.getValue(), profile_image);
                userInfo.put(EOAuth2UserServiceImpl.eKakaoEmailAttribute.getValue(), email);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    private User saveOrUpdateKakao(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmailAndLoginType(userInfo.get(EOAuth2UserServiceImpl.eKakaoEmailAttribute.getValue()).toString(), ELoginType.eKakao)
                .map(entity -> entity.update(userInfo.get(EOAuth2UserServiceImpl.eKakaoNickNameAttribute.getValue()).toString()
                        , userInfo.get(EOAuth2UserServiceImpl.eKakaoProfileImageAttribute.getValue()).toString()))
                .orElse(User.toEntityOfKakaoUser(userInfo));
        return userRepository.save(user);
    }

    public LoginResponse validateNaverAccessToken(OAuth2NaverLoginRequest oAuth2NaverLoginRequest) {

        String header = EOAuth2UserServiceImpl.eNaverBearer.getValue() + oAuth2NaverLoginRequest.getAccessToken();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(EOAuth2UserServiceImpl.eNaverAuthorization.getValue(), header);
        String responseBody = get(naverUserInfoUrl,requestHeaders);
        HashMap<String, Object> userInfo = getNaverUserInfo(responseBody);

        User user = saveOrUpdateNaver(userInfo);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(EOAuth2UserServiceImpl.eRoleUser.getValue()));
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(EOAuth2UserServiceImpl.eNaver.getValue(), EOAuth2UserServiceImpl.eNaver.getValue(), userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, EOAuth2UserServiceImpl.eNaverKeyAttribute.getValue());
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, EOAuth2UserServiceImpl.eNaverKeyAttribute.getValue());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);
        return LoginResponse.from(user.getId(), tokenInfoResponse);

    }

    private HashMap<String, Object> getNaverUserInfo(String responseBody) {
        HashMap<String, Object> userInfo = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(responseBody);

        JsonObject jsonContent = element.getAsJsonObject().get(EOAuth2UserServiceImpl.eNaverResponse.getValue()).getAsJsonObject();
        System.out.println(EOAuth2UserServiceImpl.eNaverElement.getValue() + element.toString());

        String nickname = jsonContent.get(EOAuth2UserServiceImpl.eNaverNameAttribute.getValue()).getAsString();
        String profile_image = jsonContent.get(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue()).getAsString();
        String email = jsonContent.get(EOAuth2UserServiceImpl.eNaverEmailAttribute.getValue()).getAsString();

        userInfo.put(EOAuth2UserServiceImpl.eNaverNameAttribute.getValue(), nickname);
        userInfo.put(EOAuth2UserServiceImpl.eNaverEmailAttribute.getValue(), email);
        userInfo.put(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue(), profile_image);
        return userInfo;
    }

    private User saveOrUpdateNaver(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmailAndLoginType(userInfo.get(EOAuth2UserServiceImpl.eNaverEmailAttribute.getValue()).toString(), ELoginType.eNaver)
                .map(entity -> entity.update(userInfo.get(EOAuth2UserServiceImpl.eNaverNameAttribute.getValue()).toString(),
                        userInfo.get(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue()).toString()))
                .orElse(User.toEntityOfNaverUser(userInfo));
        return userRepository.save(user);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod(EOAuth2UserServiceImpl.eNaverGetMethod.getValue());
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException(EOAuth2UserServiceImpl.eNaverApiResponseException.getValue(), e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException(EOAuth2UserServiceImpl.eNaverApiUrlException.getValue() + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException(EOAuth2UserServiceImpl.eNaverConnectionException.getValue() + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != EOAuth2UserServiceImpl.eNaverNull.getValue()) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException(EOAuth2UserServiceImpl.eNaverApiResponseException.getValue(), e);
        }
    }
}
