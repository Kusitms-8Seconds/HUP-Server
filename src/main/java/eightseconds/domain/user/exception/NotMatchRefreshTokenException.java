package eightseconds.domain.user.exception;

public class NotMatchRefreshTokenException extends RuntimeException{
    public NotMatchRefreshTokenException(String s) {
        super(s);
    }
}
