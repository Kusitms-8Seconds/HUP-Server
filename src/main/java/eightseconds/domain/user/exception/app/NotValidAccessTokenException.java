package eightseconds.domain.user.exception.app;

public class NotValidAccessTokenException extends RuntimeException{
    public NotValidAccessTokenException(String s) {
        super(s);
    }
}
