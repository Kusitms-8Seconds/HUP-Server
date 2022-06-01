package eightseconds.domain.scrap.exception;

import eightseconds.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class ScrapException extends ApplicationException {
    protected ScrapException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
