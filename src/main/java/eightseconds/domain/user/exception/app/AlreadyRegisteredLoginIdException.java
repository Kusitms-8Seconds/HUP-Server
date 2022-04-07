package eightseconds.domain.user.exception.app;

public class AlreadyRegisteredLoginIdException extends RuntimeException {
    public AlreadyRegisteredLoginIdException(String s) {
        super(s);
    }
}
