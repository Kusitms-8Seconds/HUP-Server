package eightseconds.domain.category.exeception;

public class NotFoundCategoryException extends RuntimeException{
    public NotFoundCategoryException(String s) {
        super(s);
    }
}
