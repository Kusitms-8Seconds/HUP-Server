package eightseconds.domain.myfile.exception;

import eightseconds.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class MyFileException extends ApplicationException {
    protected MyFileException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}