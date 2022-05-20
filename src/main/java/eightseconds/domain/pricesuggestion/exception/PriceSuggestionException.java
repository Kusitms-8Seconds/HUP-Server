package eightseconds.domain.pricesuggestion.exception;

import eightseconds.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class PriceSuggestionException extends ApplicationException {
    protected PriceSuggestionException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
