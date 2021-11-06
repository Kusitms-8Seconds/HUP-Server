package eightseconds.domain.user.exception;

public class InvalidIdToken extends IllegalArgumentException{

    public InvalidIdToken(String s) {
        super(s);
    }
}
