package eightseconds.domain.user.exception.app;

public class WrongRefreshTokenRequestException extends RuntimeException{
    public WrongRefreshTokenRequestException(String s) {
        super(s);
    }
}
