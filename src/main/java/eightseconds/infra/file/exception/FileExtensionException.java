package eightseconds.infra.file.exception;

import eightseconds.infra.file.constant.FileConstants.EFileExceptionMessage;

public class FileExtensionException extends IllegalArgumentException {
    public FileExtensionException() {super(EFileExceptionMessage.eFileExtensionExceptionMessage.getValue());}
}
