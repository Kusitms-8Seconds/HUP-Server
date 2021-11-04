package eightseconds.domain.file.exception;

public class FileToSaveNotExistException extends IllegalArgumentException {

    public FileToSaveNotExistException(String s) {
        super(s);
    }
}
