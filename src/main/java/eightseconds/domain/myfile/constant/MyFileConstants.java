package eightseconds.domain.myfile.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class MyFileConstants {

    @Getter
    @RequiredArgsConstructor
    public enum MyFileExceptionList {
        NOT_FOUND_FILE("F0001", HttpStatus.NOT_FOUND, "해당 fileId로 MyFile을 찾을 수 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
