package eightseconds.domain.notice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    @AllArgsConstructor
    public enum ENoticeServiceImpl{
        eNotFoundNoticeExceptionMessage("유효하지 않은 id입니다.");
        private final String value;
    }
}
