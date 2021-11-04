package eightseconds.domain.file.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileConstants {

    @Getter
    @AllArgsConstructor
    public enum EFileServiceImpl {
        BASE_DIR("user.dir"),
        IMAGES_DIR("images/"),
        FILE_NOT_FOUND_EXCEPTION_MESSAGE("파일이 존재하지 않습니다."),
        FILE_TO_SAVE_NOT_EXIST_EXCEPTION_MESSAGE("저장할 파일이 존재하지 않습니다.");

        private final String message;
    }

}
