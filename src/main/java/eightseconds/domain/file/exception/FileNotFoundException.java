package eightseconds.domain.file.exception;

public class FileNotFoundException extends IllegalArgumentException{

    public FileNotFoundException(String s) {
        super(s);
    }
}
