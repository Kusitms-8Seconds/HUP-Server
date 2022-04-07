package eightseconds.domain.user.exception.oauth2;

public class InvalidIdTokenException extends RuntimeException{
    public InvalidIdTokenException(String s) {
        super(s);
    }
}
