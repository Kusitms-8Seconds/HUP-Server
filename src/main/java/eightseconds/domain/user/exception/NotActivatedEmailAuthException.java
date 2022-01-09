package eightseconds.domain.user.exception;

public class NotActivatedEmailAuthException extends RuntimeException{
    public NotActivatedEmailAuthException(String s) {
        super(s);
    }
}
