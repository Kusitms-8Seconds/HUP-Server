package eightseconds.domain.item.exception;

public class NotSoldOutTimeException extends IllegalArgumentException{

    public NotSoldOutTimeException(String s) {
        super(s);
    }
}
