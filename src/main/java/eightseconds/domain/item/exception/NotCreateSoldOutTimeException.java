package eightseconds.domain.item.exception;

public class NotCreateSoldOutTimeException extends IllegalArgumentException{

    public NotCreateSoldOutTimeException(String s) {
        super(s);
    }
}
