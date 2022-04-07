package eightseconds.domain.user.exception.app;

public class NotFoundRegisteredUserException extends RuntimeException{
    public NotFoundRegisteredUserException(String s) {
        super(s);
    }
}
