package eightseconds.domain.pricesuggestion.exception;

public class SameUserIdException extends RuntimeException{
    public SameUserIdException(String s) {
        super(s);
    }
}
