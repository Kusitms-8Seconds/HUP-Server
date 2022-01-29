package eightseconds.domain.user.exception;

public class AlreadyRegisteredLoginIdException extends RuntimeException {
    public AlreadyRegisteredLoginIdException(String s) {
        super(s);
    }
}
