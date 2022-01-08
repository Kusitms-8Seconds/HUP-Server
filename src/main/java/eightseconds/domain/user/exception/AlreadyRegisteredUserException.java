package eightseconds.domain.user.exception;

public class AlreadyRegisteredUserException extends RuntimeException{
    public AlreadyRegisteredUserException(String s) {
        super(s);
    }
}
