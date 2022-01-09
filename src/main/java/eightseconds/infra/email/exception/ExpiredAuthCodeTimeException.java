package eightseconds.infra.email.exception;

public class ExpiredAuthCodeTimeException extends RuntimeException{
    public ExpiredAuthCodeTimeException(String s) {
        super(s);
    }
}
