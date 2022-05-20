package eightseconds.domain.item.exception;

import eightseconds.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ItemException extends ApplicationException {
    protected ItemException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
