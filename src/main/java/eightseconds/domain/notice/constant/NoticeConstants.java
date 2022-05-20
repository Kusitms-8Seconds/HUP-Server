package eightseconds.domain.notice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class NoticeConstants {

    @Getter
    @AllArgsConstructor
    public enum ENoticeApiController{
        eLocationIdPath("/{id}"),
        ePostMethod("post"),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        ePutMethod("put");
        private final String value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum NoticeExceptionList {
        NOT_FOUND_NOTICE("NT0001", HttpStatus.NOT_FOUND, "해당 ID로 공지를 찾을 수 없습니다.");

        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}
