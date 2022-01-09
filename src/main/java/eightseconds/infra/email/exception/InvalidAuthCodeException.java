package eightseconds.infra.email.exception;

public class InvalidAuthCodeException extends RuntimeException{
    public InvalidAuthCodeException(String s) {
        super(s);
    }
}
