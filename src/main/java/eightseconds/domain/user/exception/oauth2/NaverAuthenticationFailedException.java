package eightseconds.domain.user.exception.oauth2;

public class NaverAuthenticationFailedException extends RuntimeException{
    public NaverAuthenticationFailedException(String s) {
        super(s);
    }
}
