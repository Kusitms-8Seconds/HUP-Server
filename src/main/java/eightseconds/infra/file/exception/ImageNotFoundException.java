package eightseconds.infra.file.exception;

import eightseconds.infra.file.constant.FileConstants.EFileExceptionMessage;

public class ImageNotFoundException extends IllegalArgumentException {
    public ImageNotFoundException() {super(EFileExceptionMessage.eImageNotFoundExceptionMessage.getValue());}
}
