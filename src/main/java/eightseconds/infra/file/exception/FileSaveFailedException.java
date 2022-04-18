package eightseconds.infra.file.exception;

import eightseconds.infra.file.constant.FileConstants.EFileExceptionMessage;

public class FileSaveFailedException extends IllegalArgumentException {
    public FileSaveFailedException() {super(EFileExceptionMessage.eFileSaveFailedExceptionMessage.getValue());}
}
