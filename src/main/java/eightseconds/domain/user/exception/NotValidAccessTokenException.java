package eightseconds.domain.user.exception;

public class NotValidAccessTokenException extends RuntimeException{
    public NotValidAccessTokenException(String s) {
        super(s);
    }
}
