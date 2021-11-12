package eightseconds.domain.pricesuggestion.exception;

public class AlreadySoldOutException extends IllegalArgumentException{
    public AlreadySoldOutException(String s) {
        super(s);
    }
}