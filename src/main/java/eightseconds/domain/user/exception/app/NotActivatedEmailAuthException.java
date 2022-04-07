package eightseconds.domain.user.exception.app;

public class NotActivatedEmailAuthException extends RuntimeException{
    public NotActivatedEmailAuthException(String s) {
        super(s);
    }
}
