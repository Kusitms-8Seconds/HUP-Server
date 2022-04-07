package eightseconds.domain.user.exception.app;

public class NotValidRefreshTokenException extends RuntimeException{
    public NotValidRefreshTokenException(String s) {
        super(s);
    }
}
