package eightseconds.domain.user.exception;

public class InvalidIdToken extends RuntimeException{
    public InvalidIdToken(String s) {
        super(s);
    }
}
