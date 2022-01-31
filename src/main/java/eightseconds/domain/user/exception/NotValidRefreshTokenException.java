package eightseconds.domain.user.exception;

public class NotValidRefreshTokenException extends RuntimeException{
    public NotValidRefreshTokenException(String s) {
        super(s);
    }
}
