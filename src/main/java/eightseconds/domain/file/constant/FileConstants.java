package eightseconds.domain.file.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileConstants {

    @Getter
    @AllArgsConstructor
    public enum EFileApiController {
        eBaseDir("user.dir"),
        eImagesDir("/src/main/resources/images/"),
        eFileNotFoundExceptionMessage("해당 파일을 찾을 수 없습니다.");

        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum EFileServiceImpl {
        eBaseDir("user.dir"),
        eImagesDir("/src/main/resources/images/"),
        eSlash("/"),
        eDot("."),
        eFileNotFoundExceptionMessage("파일이 존재하지 않습니다."),
        eFileToSaveNotExistExceptionMessage("저장할 파일이 존재하지 않습니다.");
        private final String value;
    }

}
