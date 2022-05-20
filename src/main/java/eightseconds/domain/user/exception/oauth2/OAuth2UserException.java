package eightseconds.domain.user.exception.oauth2;

import eightseconds.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class OAuth2UserException extends ApplicationException {

    protected OAuth2UserException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
