package eightseconds.domain.notice.dto;

import eightseconds.domain.notice.entity.Notice;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@ApiModel(description = "공지사항 목록을 조회하기 위한 응답 객체")
public class NoticeListResponse {

    @NotNull private Long id;
    @NotNull private String userName;
    @NotNull private String title;

    public static NoticeListResponse from(Notice notice) {
        return NoticeListResponse.builder()
                .id(notice.getId())
                .userName(notice.getUser().getUsername())
                .title(notice.getTitle())
                .build();
    }
}
