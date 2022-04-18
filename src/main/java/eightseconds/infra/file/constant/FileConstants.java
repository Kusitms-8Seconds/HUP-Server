package eightseconds.infra.file.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class FileConstants {
    @Getter
    @AllArgsConstructor
    public enum EFileExceptionMessage {
        eFileExtensionExceptionMessage("이미지 파일이 아닙니다."),
        eFileLoadFailedExceptionMessage("파일 불러오기에 실패했습니다."),
        eFileSaveFailedExceptionMessage("파일 저장에 실패했습니다."),
        eImageNotFoundExceptionMessage("해당 이미지가 존재하지 않습니다.");
        private final String value;
    }
}
