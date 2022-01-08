package eightseconds.domain.user.exception;

public class UserNotActivatedException extends RuntimeException{
    public UserNotActivatedException(String s) {
        super(s);
    }
}
