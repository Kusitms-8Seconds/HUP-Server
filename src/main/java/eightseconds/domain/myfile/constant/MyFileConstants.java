package eightseconds.domain.myfile.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MyFileConstants {
    @Getter
    @AllArgsConstructor
    public enum EMyFileExceptionMessage {
        eNotFoundMyFileExceptionMessage("해당 fileId로 MyFile을 찾을 수 없습니다.");
        private final String message;
    }
}
