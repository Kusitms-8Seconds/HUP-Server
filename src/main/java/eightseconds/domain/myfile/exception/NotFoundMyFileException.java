package eightseconds.domain.myfile.exception;


import eightseconds.domain.myfile.constant.MyFileConstants;

public class NotFoundMyFileException extends IllegalArgumentException{
    public NotFoundMyFileException() {
        super(MyFileConstants.EMyFileExceptionMessage.eNotFoundMyFileExceptionMessage.getMessage());
    }
}
