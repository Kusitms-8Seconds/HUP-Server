package eightseconds.domain.user.exception;

public class WrongRefreshTokenRequestException extends RuntimeException{
    public WrongRefreshTokenRequestException(String s) {
        super(s);
    }
}
