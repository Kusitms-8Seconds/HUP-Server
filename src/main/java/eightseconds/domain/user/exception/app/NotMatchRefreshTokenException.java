package eightseconds.domain.user.exception.app;

public class NotMatchRefreshTokenException extends RuntimeException{
    public NotMatchRefreshTokenException(String s) {
        super(s);
    }
}
