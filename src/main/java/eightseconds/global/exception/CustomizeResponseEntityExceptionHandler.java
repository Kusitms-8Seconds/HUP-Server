package eightseconds.global.exception;

import eightseconds.domain.file.exception.FileNotFoundException;
import eightseconds.domain.file.exception.FileToSaveNotExistException;
import eightseconds.domain.item.exception.*;
import eightseconds.domain.notice.exception.NotFoundNoticeException;
import eightseconds.domain.pricesuggestion.exception.*;
import eightseconds.domain.scrap.exception.AlreadyScrapException;
import eightseconds.domain.scrap.exception.NotExistingScrapOfUserException;
import eightseconds.domain.user.exception.*;
import eightseconds.infra.email.exception.ExpiredAuthCodeTimeException;
import eightseconds.infra.email.exception.InvalidAuthCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Global Domain Exception
     */

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRunTimeExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), status.toString(), errors);

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * User Domain Exception
     */

    @ExceptionHandler(AlreadyRegisteredUserException.class)
    public final ResponseEntity<Object> handleAlreadyRegisteredUserException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public final ResponseEntity<Object> handleNotFoundUserException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public final ResponseEntity<Object> handleUserNotActivatedException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.FORBIDDEN.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidIdToken.class)
    public final ResponseEntity<Object> handleInvalidIdTokenException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotActivatedEmailAuthException.class)
    public final ResponseEntity<Object> handleNotActivatedEmailAuthException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundRegisteredUserException.class)
    public final ResponseEntity<Object> handleNotFoundRegisteredUserException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyRegisteredLoginIdException.class)
    public final ResponseEntity<Object> handleAlreadyRegisteredLoginIdException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotMatchRefreshTokenException.class)
    public final ResponseEntity<Object> handleNotMatchRefreshTokenException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotValidRefreshTokenException.class)
    public final ResponseEntity<Object> handleNotValidRefreshTokenException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WrongRefreshTokenRequestException.class)
    public final ResponseEntity<Object> handleWrongRefreshTokenRequestException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotValidAccessTokenException.class)
    public final ResponseEntity<Object> handleNotValidAccessTokenException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }


    /**
     * Email Exception
     */

    @ExceptionHandler(InvalidAuthCodeException.class)
    public final ResponseEntity<Object> handleInvalidAuthCodeException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredAuthCodeTimeException.class)
    public final ResponseEntity<Object> handleExpiredAuthCodeTimeException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.REQUEST_TIMEOUT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.REQUEST_TIMEOUT);
    }

    /**
     * Item Exception
     */

    @ExceptionHandler(NotDesirableAuctionEndTimeException.class)
    public final ResponseEntity<Object> handleNotDesirableAuctionEndTimeException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(NotFoundItemException.class)
    public final ResponseEntity<Object> handleNotFoundItemException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidItemSoldStatusException.class)
    public final ResponseEntity<Object> handleInvalidItemSoldStatusException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public final ResponseEntity<Object> handleInvalidCategoryException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(NotOnGoingException.class)
    public final ResponseEntity<Object> handleNotOnGoingException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotBidItemException.class)
    public final ResponseEntity<Object> handleNotBidItemException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotSoldOutTimeException.class)
    public final ResponseEntity<Object> handleNotSoldOutTimeException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }


    /**
     * File Exception
     */

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<Object> handleFileNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileToSaveNotExistException.class)
    public final ResponseEntity<Object> handleFileToSaveNotExistException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * Scrap Exception
     */

    @ExceptionHandler(AlreadyScrapException.class)
    public final ResponseEntity<Object> handleAlreadyScrapException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotExistingScrapOfUserException.class)
    public final ResponseEntity<Object> handleNotExistingScrapOfUserException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * PriceSuggestion Exception
     */

    @ExceptionHandler(AlreadySoldOutException.class)
    public final ResponseEntity<Object> handleAlreadySoldOutException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PriorPriceSuggestionException.class)
    public final ResponseEntity<Object> handlePriorPriceSuggestionException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundPriceSuggestionException.class)
    public final ResponseEntity<Object> handleNotFoundPriceSuggestionException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SameUserIdException.class)
    public final ResponseEntity<Object> handleSameUserIdException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InitPriceException.class)
    public final ResponseEntity<Object> handleInitPriceException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }

    /**
     * notice Exception
     */

    @ExceptionHandler(NotFoundNoticeException.class)
    public final ResponseEntity<Object> handleNotFoundNoticeException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
