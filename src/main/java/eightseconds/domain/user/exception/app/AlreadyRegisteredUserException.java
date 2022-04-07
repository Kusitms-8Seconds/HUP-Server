package eightseconds.domain.user.exception.app;

public class AlreadyRegisteredUserException extends RuntimeException{
    public AlreadyRegisteredUserException(String s) {
        super(s);
    }
}
