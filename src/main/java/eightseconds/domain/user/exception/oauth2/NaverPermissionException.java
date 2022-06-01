package eightseconds.domain.user.exception.oauth2;

import static eightseconds.domain.user.constant.UserConstants.OAuth2UserExceptionList.NAVER_PERMISSION;

public class NaverPermissionException extends OAuth2UserException{
    public NaverPermissionException() {
        super(NAVER_PERMISSION.getErrorCode(),
                NAVER_PERMISSION.getHttpStatus(),
                NAVER_PERMISSION.getMessage()
        );
    }
}
