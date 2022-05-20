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
import eightseconds.domain.user.constant.UserConstants.EAuthority;
import eightseconds.domain.user.constant.UserConstants.EGoogleUser;
import eightseconds.domain.user.constant.UserConstants.EKakaoUser;
import eightseconds.domain.user.constant.UserConstants.ENaverUser;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.dto.*;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.oauth2.*;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.OAuth2Attribute;
import eightseconds.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    /**
     * Validate Google
     */

    @Transactional
    public LoginResponse validateGoogleIdToken(OAuth2GoogleLoginRequest oAuth2LoginRequest) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .setIssuer(googleIssuer)
                .build();
        GoogleIdToken idToken = verifier.verify(oAuth2LoginRequest.getIdToken());
        if(idToken != null) return saveUserOrUpdateByGoogleIdToken(idToken, oAuth2LoginRequest.getTargetToken());
        else throw new GoogleInvalidIdTokenException();
    }

    public LoginResponse saveUserOrUpdateByGoogleIdToken(GoogleIdToken idToken, String targetToken) {
        Payload payload = idToken.getPayload();
        User user = saveOrUpdateGoogleUser(payload);
        TokenInfoResponse tokenInfoResponse = makeAppToken(payload);
        updateTargetToken(user, targetToken);
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    private User saveOrUpdateGoogleUser(Payload payload) {
        User user = userRepository.findByEmailAndLoginType(payload.getEmail(), ELoginType.eGoogle)
                .map(entity -> entity.update((String) payload.get(EGoogleUser.eGoogleNameAttribute.getValue()),
                        (String) payload.get(EGoogleUser.eGooglePictureAttribute.getValue())))
                .orElse(User.toGoogleUserEntity(payload));
        return userRepository.save(user);
    }

    private TokenInfoResponse makeAppToken(Payload payload) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(EAuthority.eRoleUser.getValue()));
        Map<String, Object> map = new HashMap<>();
        map.put(EGoogleUser.eGoogleIdAttribute.getValue(), EGoogleUser.eGoogleSub.getValue());
        map.put(EGoogleUser.eGoogleKeyAttribute.getValue(), EGoogleUser.eGoogleSub.getValue());
        map.put(EGoogleUser.eGoogleNameAttribute.getValue(), payload.get(EGoogleUser.eGoogleNameAttribute.getValue()));
        map.put(EGoogleUser.eGoogleEmailAttribute.getValue(), payload.getEmail());
        map.put(EGoogleUser.eGooglePictureAttribute.getValue(), payload.get(EGoogleUser.eGooglePictureAttribute.getValue()));
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(EGoogleUser.eGoogle.getValue(), EGoogleUser.eGoogleSub.getValue(), map);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, EGoogleUser.eGoogleKeyAttribute.getValue());
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, EGoogleUser.eGoogleKeyAttribute.getValue());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);
        return tokenInfoResponse;
    }

    public void updateTargetToken(User user, String targetToken) {
        user.setTargetToken(targetToken);
    }

    /**
     * Validate Kakao
     */

    @Override
    @Transactional
    public LoginResponse validateKakaoAccessToken(OAuth2KakaoLoginRequest oAuth2KakaoLoginRequest) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(EAuthority.eRoleUser.getValue()));
        HashMap<String, Object> userInfo = getUserInfo(oAuth2KakaoLoginRequest.getAccessToken());
        User user = saveOrUpdateKakao(userInfo);
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(EKakaoUser.eKakao.getValue(), EKakaoUser.eKakao.getValue(), userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, EKakaoUser.eKakaoKeyAttribute.getValue());
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, EKakaoUser.eKakaoKeyAttribute.getValue());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);
        updateTargetToken(user, oAuth2KakaoLoginRequest.getTargetToken());
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    public HashMap<String, Object> getUserInfo(String access_Token) {

        HashMap<String, Object> userInfo = new HashMap<>();
        try {
            URL url = new URL(kakaoUserInfoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(EKakaoUser.eKakaoGetMethod.getValue());
            conn.setRequestProperty(EKakaoUser.eKakaoAuthorization.getValue(), EKakaoUser.eKakaoBearer.getValue() + access_Token);
            conn.setRequestProperty(EKakaoUser.eKakaoContentType.getValue(), EKakaoUser.eKakaoContentTypeUrlencoded.getValue());

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            String result = EKakaoUser.eKakaoEmpty.getValue();

            while ((line = br.readLine()) != null) {
                result += line;}

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject kakao_account = element.getAsJsonObject().get(EKakaoUser.eKakaoAccountAttribute.getValue()).getAsJsonObject();

            if(kakao_account != null){
                JsonObject profile = kakao_account.getAsJsonObject().get(EKakaoUser.eKakaoProfile.getValue()).getAsJsonObject();
                String nickname = profile.getAsJsonObject().get(EKakaoUser.eKakaoNickNameAttribute.getValue()).getAsString();
                String profile_image = profile.getAsJsonObject().get(EKakaoUser.eKakaoProfileImageAttribute.getValue()).getAsString();
                String email = kakao_account.getAsJsonObject().get(EKakaoUser.eKakaoEmailAttribute.getValue()).getAsString();
                userInfo.put(EKakaoUser.eKakaoNickNameAttribute.getValue(), nickname);
                userInfo.put(EKakaoUser.eKakaoProfileImageAttribute.getValue(), profile_image);
                userInfo.put(EKakaoUser.eKakaoEmailAttribute.getValue(), email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    private User saveOrUpdateKakao(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmailAndLoginType(userInfo.get(EKakaoUser.eKakaoEmailAttribute.getValue()).toString(), ELoginType.eKakao)
                .map(entity -> entity.update(userInfo.get(EKakaoUser.eKakaoNickNameAttribute.getValue()).toString()
                        , userInfo.get(EKakaoUser.eKakaoProfileImageAttribute.getValue()).toString()))
                .orElse(User.toKakaoUserEntity(userInfo));
        return userRepository.save(user);
    }

    /**
     * Validate Naver
     */

    @Transactional
    public LoginResponse validateNaverAccessToken(OAuth2NaverLoginRequest oAuth2NaverLoginRequest) {
        String header = ENaverUser.eNaverBearer.getValue() + oAuth2NaverLoginRequest.getAccessToken();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(ENaverUser.eNaverAuthorization.getValue(), header);
        String responseBody = get(naverUserInfoUrl,requestHeaders);
        HashMap<String, Object> userInfo = getNaverUserInfo(responseBody);

        User user = saveOrUpdateNaver(userInfo);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(EAuthority.eRoleUser.getValue()));
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(ENaverUser.eNaver.getValue(), ENaverUser.eNaver.getValue(), userInfo);
        var memberAttribute = oAuth2Attribute.convertToMap();
        OAuth2User userDetails = new DefaultOAuth2User(authorities, memberAttribute, ENaverUser.eNaverKeyAttribute.getValue());
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(userDetails, authorities, ENaverUser.eNaverKeyAttribute.getValue());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth);
        updateTargetToken(user, oAuth2NaverLoginRequest.getTargetToken());
        return LoginResponse.from(user.getId(), tokenInfoResponse);
    }

    private HashMap<String, Object> getNaverUserInfo(String responseBody) {
        HashMap<String, Object> userInfo = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(responseBody);

        JsonObject jsonContent = element.getAsJsonObject().get(ENaverUser.eNaverResponse.getValue()).getAsJsonObject();
        System.out.println(ENaverUser.eNaverElement.getValue() + element.toString());

        String nickname = jsonContent.get(ENaverUser.eNaverNameAttribute.getValue()).getAsString();
        String profile_image = jsonContent.get(ENaverUser.eNaverProfileImageAttribute.getValue()).getAsString();
        String email = jsonContent.get(ENaverUser.eNaverEmailAttribute.getValue()).getAsString();

        userInfo.put(ENaverUser.eNaverNameAttribute.getValue(), nickname);
        userInfo.put(ENaverUser.eNaverEmailAttribute.getValue(), email);
        userInfo.put(ENaverUser.eNaverProfileImageAttribute.getValue(), profile_image);
        return userInfo;
    }

    private User saveOrUpdateNaver(HashMap<String, Object> userInfo) {
        User user = userRepository.findByEmailAndLoginType(userInfo.get(ENaverUser.eNaverEmailAttribute.getValue()).toString(), ELoginType.eNaver)
                .map(entity -> entity.update(userInfo.get(ENaverUser.eNaverNameAttribute.getValue()).toString(),
                        userInfo.get(ENaverUser.eNaverProfileImageAttribute.getValue()).toString()))
                .orElse(User.toEntityOfNaverUser(userInfo));
        return userRepository.save(user);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod(ENaverUser.eNaverGetMethod.getValue());
            for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) return readBody(con.getInputStream());
            else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) throw new NaverAuthenticationFailedException();
            else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) throw new NaverPermissionException();
            else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND) throw new NaverNotFoundException();
            else return readBody(con.getErrorStream());

        } catch (IOException e) {
            throw new NaverApiResponseException();
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new NaverApiUrlException();
        } catch (IOException e) {
            throw new NaverConnectionException();
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
            throw new NaverApiResponseException();
        }
    }
}
