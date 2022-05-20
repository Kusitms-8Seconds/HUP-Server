package eightseconds.domain.user.constant;

import eightseconds.global.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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
    }

    @Getter
    @AllArgsConstructor
    public enum EAuthority{
        eRoleDisabledUser("ROLE_DISABLED_USER"),
        eRoleUser("ROLE_USER");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum EUser{
        eNameAttribute("name"),
        eEmailAttribute("email"),
        ePictureAttribute("picture");
        private final String value;
    }


    @Getter
    @AllArgsConstructor
    public enum EGoogleUser{
        eGoogleIdAttribute("id"),
        eGoogleKeyAttribute("key"),
        eGoogleNameAttribute("name"),
        eGoogleEmailAttribute("email"),
        eGooglePictureAttribute("picture"),
        eGoogleSub("sub"),
        eGoogle("google");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum EKakaoUser{
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
        eKakao("kakao");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum ENaverUser{
        eNaverNameAttribute("name"),
        eNaverEmailAttribute("email"),
        eNaverProfileImageAttribute("profile_image"),
        eNaverKeyAttribute("key"),
        eNaverGetMethod("GET"),
        eNaverResponse("response"),
        eNaverElement("element"),
        eNaverAuthorization("Authorization"),
        eNaverBearer("Bearer "),
        eNaver("naver");
        private final String value;
    }

    @Getter
    public enum EUserServiceImpl{
        eAlreadyRegisteredEmailExceptionMessage("이미 가입되어 있는 이메일입니다."),
        eAlreadyRegisteredUserExceptionMessage("이미 가입되어 있는 유저입니다."),
        eAlreadyRegisteredLoginIdExceptionMessage("이미 등록되어 있는 아이디입니다."),
        eSuccessSignUpMessage("회원가입을 완료했습니다."),
        eUsernameNotFoundExceptionMessage(" -> 유저 이름을 데이터베이스에서 찾을 수 없습니다."),
        eUserNotActivatedExceptionMessage(" -> 유저가 활성화되어 있지 않습니다."),
        eNotFoundUserExceptionMessage("해당 유저아이디로 유저를 찾을 수 없습니다."),
        eNotFoundRegisteredUserByEmailExceptionMessage("해당 이메일로 회원가입된 유저가 없습니다."),
        eNotActivatedEmailAuthExceptionMessage("이메일 인증이 완료되지 않았습니다."),
        eNotDuplicatedLoginIdMessage("해당 아이디로 회원가입을 할 수 있습니다."),
        eNotFoundLoginIdExceptionMessage("해당 loginId로 가입된 유저가 없습니다."),
        eRefreshToken("RT:"),
        eLogout("logout"),
        eLogoutMessage("로그아웃 되었습니다."),
        eTrue(true),
        eAuthorityRoleUser("ROLE_USER"),
        eBaseFileURL("https://hup-bucket.s3.ap-northeast-2.amazonaws.com/"),
        eBasePicture("https://firebasestorage.googleapis.com/v0/b/auctionapp-f3805.appspot.com/o/profile.png?alt=media&token=655ed158-b464-4e5e-aa56-df3d7f12bdc8");

        private boolean check;
        private String value;

        EUserServiceImpl(boolean check) { this.check = check;}
        EUserServiceImpl(String value) {this.value = value;}

    }

    @Getter
    @RequiredArgsConstructor
    public enum UserExceptionList {
        NOT_FOUND_USER("U0001", HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
        NOT_FOUND_REGISTERED_USER_BY_EMAIL("U0002",HttpStatus.NOT_FOUND, "해당 이메일로 회원가입된 유저가 없습니다."),
        NOT_FOUND_LOGIN_ID("U0003",HttpStatus.NOT_FOUND, "해당 loginId로 가입된 유저가 없습니다."),
        NOT_AUTHENTICATION_FOR_CHANGE_PASSWORD("U0004",HttpStatus.CONFLICT,"비밀번호 재설정을 위한 이메일 인증이 완료되지 않았습니다."),
        NOT_ACTIVATED_EMAIL_AUTH("U0005",HttpStatus.CONFLICT,"이메일 인증이 완료되지 않았습니다."),
        ALREADY_REGISTERED_USER("U0006",HttpStatus.CONFLICT,"이미 가입되어 있는 유저입니다."),
        ALREADY_REGISTERED_LOGIN_ID("U0007",HttpStatus.CONFLICT,"이미 등록되어 있는 아이디입니다."),
        ALREADY_REGISTERED_EMAIL("U0008",HttpStatus.CONFLICT,"이미 등록되어 있는 이메일입니다."),
        NOT_MATCH_REFRESH_TOKEN("U0009",HttpStatus.UNAUTHORIZED,"Refresh Token 정보가 일치하지 않습니다."),
        NOT_SAME_PASSWORD("U0009",HttpStatus.BAD_REQUEST,"입력한 비밀번호가 서로 같지 않습니다."),
        NOT_SEND_EMAIL("U0010",HttpStatus.INTERNAL_SERVER_ERROR,"비밀번호 재설정을 위한 이메일 전송을 하지 않았습니다."),
        NOT_VALID_ACCESS_TOKEN("U0011",HttpStatus.BAD_REQUEST,"유효하지 않은 AccessToken입니다."),
        NOT_VALID_REFRESH_TOKEN("U0012",HttpStatus.BAD_REQUEST,"유효하지 않은 RefreshToken입니다."),
        USER_NOT_ACTIVATED("U0013",HttpStatus.CONFLICT,"유저가 활성화되어 있지 않습니다."),
        WRONG_REFRESH_TOKEN_REQUEST("U0014",HttpStatus.BAD_REQUEST,"잘못된 RefreshToken입니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum OAuth2UserExceptionList {
        GOOGLE_INVALID_ID_TOKEN("OU0001", HttpStatus.BAD_REQUEST, "ID token이 유효하지 않습니다."),
        NAVER_API_RESPONSE("OU0002", HttpStatus.INTERNAL_SERVER_ERROR, "NAVER API 응답을 읽는데 실패했습니다."),
        NAVER_API_URI("OU0003", HttpStatus.INTERNAL_SERVER_ERROR, "NAVER API URL이 잘못되었습니다. : "),
        NAVER_AUTHENTICATION_FAILED("OU0004", HttpStatus.UNAUTHORIZED, "Naver 인증에 실패했습니다."),
        NAVER_CONNECTION("OU0005", HttpStatus.UNAUTHORIZED, "NAVER와의 연결이 실패했습니다. : "),
        NAVER_NOT_FOUND("OU0006", HttpStatus.NOT_FOUND, "Naver API 검색 결과가 없습니다."),
        NAVER_PERMISSION("OU0006", HttpStatus.FORBIDDEN, "Naver API 호출 권한이 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum UserResponseMessage {
        SIGN_UP_SUCCESS_MESSAGE("회원가입을 완료했습니다.");

        private final String message;
    }
}
