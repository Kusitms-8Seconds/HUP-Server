package eightseconds.domain.user.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserConstants {

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
        eKakaoProfile("profile"),
        eKakaoNickNameAttribute("nickname"),
        eKakaoEmailAttribute("email"),
        eKakaoProfileImageAttribute("profile_image_url"),
        eKakaoGetMethod("GET"),
        eKakaoAuthorization("Authorization"),
        eKakaoBearer("Bearer "),
        eKakaoContentType("Content-Type"),
        eKakaoContentTypeUrlencoded("application/x-www-form-urlencoded"),
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
        eAlreadyRegisteredLoginIdExceptionMessage("이미 등록되어 있는 아이디입니다."),
        eSuccessSignUpMessage("회원가입을 완료했습니다."),
        eUsernameNotFoundExceptionMessage(" -> 유저 이름을 데이터베이스에서 찾을 수 없습니다."),
        eUserNotActivatedExceptionMessage(" -> 유저가 활성화되어 있지 않습니다."),
        eNotFoundUserExceptionMessage("해당 유저아이디로 유저를 찾을 수 없습니다."),
        eNotFoundRegisteredUserExceptionMessage("해당 이메일로 회원가입된 유저가 없습니다."),
        eNotActivatedEmailAuthExceptionMessage("이메일 인증이 완료되지 않았습니다."),
        eNotDuplicatedLoginIdMessage("해당 아이디로 회원가입을 할 수 있습니다."),
        eRefreshToken("RT:"),
        eNotValidRefreshTokenExceptionMessage("Refresh Token 정보가 유효하지 않습니다."),
        eNotMatchRefreshTokenExceptionMessage("Refresh Token 정보가 일치하지 않습니다."),
        eWrongRefreshTokenRequestExceptionMessage("잘못된 요청입니다."),
        eLogout("logout"),
        eNotValidAccessTokenExceptionMessage("잘못된 요청입니다."),
        eLogoutMessage("로그아웃 되었습니다."),
        eTrue(true),
        eAuthorityRoleUser("ROLE_USER"),
        eBaseFileURL("http://52.78.175.27:8080/api/v1/files/"),
        eBasePicture("https://firebasestorage.googleapis.com/v0/b/auctionapp-f3805.appspot.com/o/profile.png?alt=media&token=655ed158-b464-4e5e-aa56-df3d7f12bdc8");

        private boolean check;
        private String value;

        EUserServiceImpl(boolean check) { this.check = check;}
        EUserServiceImpl(String value) {this.value = value;}

    }

}
