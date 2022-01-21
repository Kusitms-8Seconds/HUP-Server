package eightseconds.domain.user.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserConstants {

    SUCCESS_SIGN_UP("회원가입을 완료했습니다.");

    private final String message;

    @Getter
    @AllArgsConstructor
    public enum EOAuth2UserApiController{
        eBearerHeaderValue("Bearer "),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        eUpdateMethod("update");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum EUserApiController{
        eLocationIdPath("/{id}"),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        eUpdateMethod("update");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum ELoginType{
        eGoogle("구글"),
        eNaver("네이버"),
        eKakao("카카오"),
        eApp("앱");

        private final String name;

        @JsonCreator
        public static ELoginType from(String s) {
            return ELoginType.valueOf(s);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum EOAuth2UserServiceImpl{
        eRoleUser("ROLE_USER"),
        eGoogleIdAttribute("id"),
        eGoogleKeyAttribute("key"),
        eGoogleNameAttribute("name"),
        eGoogleEmailAttribute("email"),
        eGooglePictureAttribute("picture"),
        eGoogleSub("sub"),
        eGoogle("google"),
        eGoogleInvalidIdTokenMessage("ID token이 유효하지 않습니다."),
        eKakaoKeyAttribute("key"),
        eKakaoNickNameAttribute("nickname"),
        eKakaoEmailAttribute("email"),
        eKakaoProfileImageAttribute("profile_image"),
        eKakaoGetMethod("GET"),
        eKakaoAuthorization("Authorization"),
        eKakaoBearer("Bearer "),
        eKakaoResponseCode("responseCode : "),
        eKakaoEmpty(""),
        eKakaoPropertiesAttribute("properties"),
        eKakaoAccountAttribute("kakao_account"),
        eKakao("kakao"),
        eNaverNameAttribute("name"),
        eNaverEmailAttribute("email"),
        eNaverProfileImageAttribute("profile_image"),
        eNaverKeyAttribute("key"),
        eNaverGetMethod("GET"),
        eNaverResponse("response"),
        eNaverElement("element"),
        eNaverAuthorization("Authorization"),
        eNaverBearer("Bearer "),
        eNaverApiUrlException("API URL이 잘못되었습니다. : "),
        eNaverConnectionException("연결이 실패했습니다. : "),
        eNaverApiResponseException("API 응답을 읽는데 실패했습니다."),
        eNaverNull(null),
        eNaver("naver");

        private final String value;

        @JsonCreator
        public static EOAuth2UserServiceImpl from(String s) {
            return EOAuth2UserServiceImpl.valueOf(s);
        }
    }

    @Getter
    public enum EUserServiceImpl{
        eAlreadyRegisteredUserExceptionMessage("이미 가입되어 있는 유저입니다."),
        eSuccessSignUpMessage("회원가입을 완료했습니다."),
        eUsernameNotFoundException(" -> 유저 이름을 데이터베이스에서 찾을 수 없습니다."),
        eUserNotActivatedException(" -> 유저가 활성화되어 있지 않습니다."),
        eNotFoundUserException("해당 유저아이디로 유저를 찾을 수 없습니다."),
        eNotActivatedEmailAuthException("이메일 인증이 완료되지 않았습니다."),
        eTrue(true),
        eAuthorityRoleUser("ROLE_USER");

        private boolean check;
        private String value;

        EUserServiceImpl(boolean check) { this.check = check;}
        EUserServiceImpl(String value) {this.value = value;}

    }


}
