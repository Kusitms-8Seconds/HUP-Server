package eightseconds.domain.user.exception.app;

public class AlreadyRegisteredEmailException extends RuntimeException {
    public AlreadyRegisteredEmailException(String s) {
        super(s);
    }
}
