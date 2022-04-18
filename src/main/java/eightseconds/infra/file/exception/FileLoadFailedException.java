package eightseconds.infra.file.exception;

import eightseconds.infra.file.constant.FileConstants.EFileExceptionMessage;

public class FileLoadFailedException extends IllegalArgumentException {
    public FileLoadFailedException() {super(EFileExceptionMessage.eFileLoadFailedExceptionMessage.getValue());}
}
