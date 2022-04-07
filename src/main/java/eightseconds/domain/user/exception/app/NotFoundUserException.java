package eightseconds.domain.user.exception.app;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(String s) {
        super(s);
    }
}
