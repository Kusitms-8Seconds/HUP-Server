//package eightseconds.global.exception;
//
//import eightseconds.domain.category.exeception.NotFoundCategoryException;
//import eightseconds.domain.category.exeception.NotFoundUserInterestCategoryException;
//import eightseconds.domain.chatroom.exception.AlreadyEnterException;
//import eightseconds.domain.chatroom.exception.NotFoundChatRoomException;
//import eightseconds.domain.chatroom.exception.NotFoundUserChatRoomException;
//import eightseconds.domain.item.exception.*;
//import eightseconds.domain.myfile.exception.NotFoundMyFileException;
//import eightseconds.domain.notice.exception.NotFoundNoticeException;
//import eightseconds.domain.pricesuggestion.exception.*;
//import eightseconds.domain.scrap.exception.AlreadyScrapException;
//import eightseconds.domain.scrap.exception.NotExistingScrapOfUserException;
//import eightseconds.domain.user.exception.app.NotFoundUserException;
//import eightseconds.domain.user.exception.app.*;
//import eightseconds.domain.user.exception.oauth2.*;
//import eightseconds.global.dto.ExceptionResponse;
//import eightseconds.infra.email.exception.ExpiredAuthCodeTimeException;
//import eightseconds.infra.email.exception.InvalidAuthCodeException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestControllerAdvice
//public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    /**
//     * Global Domain Exception
//     */
//
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public final ResponseEntity<Object> handleRunTimeExceptions(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request) {
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(x -> x.getDefaultMessage())
//                .collect(Collectors.toList());
//        ExceptionResponse exceptionResponse = new ExceptionResponse(status.toString(), errors);
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    /**
//     * User Domain Exception
//     */
//
//    @ExceptionHandler(AlreadyRegisteredUserException.class)
//    public final ResponseEntity<Object> handleAlreadyRegisteredUserException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NotFoundUserException.class)
//    public final ResponseEntity<Object> handleNotFoundUserException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NotActivatedEmailAuthException.class)
//    public final ResponseEntity<Object> handleNotActivatedEmailAuthException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(NotFoundRegisteredUserByEmailExceptionMessage.class)
//    public final ResponseEntity<Object> handleNotFoundRegisteredUserException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(AlreadyRegisteredLoginIdException.class)
//    public final ResponseEntity<Object> handleAlreadyRegisteredLoginIdException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NotMatchRefreshTokenException.class)
//    public final ResponseEntity<Object> handleNotMatchRefreshTokenException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(NotValidRefreshTokenException.class)
//    public final ResponseEntity<Object> handleNotValidRefreshTokenException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(WrongRefreshTokenRequestException.class)
//    public final ResponseEntity<Object> handleWrongRefreshTokenRequestException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(NotValidAccessTokenException.class)
//    public final ResponseEntity<Object> handleNotValidAccessTokenException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(AlreadyRegisteredEmailException.class)
//    public final ResponseEntity<Object> handleAlreadyRegisteredEmailException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
////    @ExceptionHandler(NotFoundRegisteredEmailException.class)
////    public final ResponseEntity<Object> handleNotFoundRegisteredEmailException(Exception ex, WebRequest request) {
////        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
////        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
////    }
//
//    @ExceptionHandler(NotSamePasswordException.class)
//    public final ResponseEntity<Object> handleNotSamePasswordException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NotAuthenticationForChangePasswordException.class)
//    public final ResponseEntity<Object> handleNotAuthenticationPasswordException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(NotSendEmailException.class)
//    public final ResponseEntity<Object> handleNotSendEmailException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public final ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(UserNotActivatedException.class)
//    public final ResponseEntity<Object> handleUserNotActivatedException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(NotFoundLoginIdException.class)
//    public final ResponseEntity<Object> handleNotFoundLoginIdException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    /**
//     * Google User Domain Exception
//     */
//
//    @ExceptionHandler(GoogleInvalidIdTokenException.class)
//    public final ResponseEntity<Object> handleInvalidIdTokenException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    /**
//     * Naver User Domain Exception
//     */
//
//    @ExceptionHandler(NaverApiResponseException.class)
//    public final ResponseEntity<Object> handleNaverApiResponseException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(NaverApiUrlException.class)
//    public final ResponseEntity<Object> handleNaverApiUrlException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(NaverConnectionException.class)
//    public final ResponseEntity<Object> handleNaverConnectionException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(NaverAuthenticationFailedException.class)
//    public final ResponseEntity<Object> handleNaverAuthenticationFailedException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(NaverPermissionException.class)
//    public final ResponseEntity<Object> handleNaverPermissionException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(NaverNotFoundException.class)
//    public final ResponseEntity<Object> handleNaverNotFoundException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    /**
//     * Email Exception
//     */
//
//    @ExceptionHandler(InvalidAuthCodeException.class)
//    public final ResponseEntity<Object> handleInvalidAuthCodeException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(ExpiredAuthCodeTimeException.class)
//    public final ResponseEntity<Object> handleExpiredAuthCodeTimeException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.REQUEST_TIMEOUT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.REQUEST_TIMEOUT);
//    }
//
//    /**
//     * Item Exception
//     */
//
//    @ExceptionHandler(NotDesirableAuctionEndTimeException.class)
//    public final ResponseEntity<Object> handleNotDesirableAuctionEndTimeException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
//    }
//
//    @ExceptionHandler(NotFoundItemException.class)
//    public final ResponseEntity<Object> handleNotFoundItemException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidItemSoldStatusException.class)
//    public final ResponseEntity<Object> handleInvalidItemSoldStatusException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
//    }
//
//    @ExceptionHandler(InvalidCategoryException.class)
//    public final ResponseEntity<Object> handleInvalidCategoryException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.PRECONDITION_FAILED.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.PRECONDITION_FAILED);
//    }
//
//    @ExceptionHandler(NotOnGoingException.class)
//    public final ResponseEntity<Object> handleNotOnGoingException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NotBidItemException.class)
//    public final ResponseEntity<Object> handleNotBidItemException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NotSoldOutTimeException.class)
//    public final ResponseEntity<Object> handleNotSoldOutTimeException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//
//    /**
//     * MyFile Domain Exception
//     */
//
//    @ExceptionHandler(NotFoundMyFileException.class)
//    public final ResponseEntity<Object> handleNotFoundMyFileException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    /**
//     * Scrap Exception
//     */
//
//    @ExceptionHandler(AlreadyScrapException.class)
//    public final ResponseEntity<Object> handleAlreadyScrapException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NotExistingScrapOfUserException.class)
//    public final ResponseEntity<Object> handleNotExistingScrapOfUserException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    /**
//     * PriceSuggestion Exception
//     */
//
//    @ExceptionHandler(AlreadySoldOutException.class)
//    public final ResponseEntity<Object> handleAlreadySoldOutException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(PriorPriceSuggestionException.class)
//    public final ResponseEntity<Object> handlePriorPriceSuggestionException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NotFoundPriceSuggestionException.class)
//    public final ResponseEntity<Object> handleNotFoundPriceSuggestionException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(SameUserIdException.class)
//    public final ResponseEntity<Object> handleSameUserIdException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(InitPriceException.class)
//    public final ResponseEntity<Object> handleInitPriceException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(AuctionClosingTimeException.class)
//    public final ResponseEntity<Object> handleAuctionClosingTimeException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    /**
//     * notice Exception
//     */
//
//    @ExceptionHandler(NotFoundNoticeException.class)
//    public final ResponseEntity<Object> handleNotFoundNoticeException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    /**
//     * chatRoom Exception
//     */
//
//    @ExceptionHandler(NotFoundChatRoomException.class)
//    public final ResponseEntity<Object> handleNotFoundChatRoomException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NotFoundUserChatRoomException.class)
//    public final ResponseEntity<Object> handleNotFoundUserChatRoomException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(AlreadyEnterException.class)
//    public final ResponseEntity<Object> handleAlreadyEnterException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    /**
//     * Category Exception
//     */
//
//    @ExceptionHandler(NotFoundCategoryException.class)
//    public final ResponseEntity<Object> handleNotFoundCategoryException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NotFoundUserInterestCategoryException.class)
//    public final ResponseEntity<Object> handleNotFoundUserInterestCategoryException(Exception ex, WebRequest request) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), Arrays.asList(ex.getMessage()));
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//}
