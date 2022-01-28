package eightseconds.domain.user.exception;

public class NotFoundRegisteredUserException extends RuntimeException{
    public NotFoundRegisteredUserException(String s) {
        super(s);
    }
}
