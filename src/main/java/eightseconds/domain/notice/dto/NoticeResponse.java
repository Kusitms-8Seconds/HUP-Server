package eightseconds.domain.notice.dto;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.notice.entity.Notice;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ApiModel(description = "공지사항을 등록, 조회하기 위한 응답 객체")
public class NoticeResponse {

    @NotNull private Long id;
    @NotNull private Long userId;
    @NotNull private String userName;
    private String title;
    private String body;
    private List<String> fileNames;

    public static NoticeResponse from(Notice notice) {
        List<String> fileNames = new ArrayList<>();
        if (!notice.getMyFiles().isEmpty()) {
            List<MyFile> myFiles = notice.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }

        return NoticeResponse.builder()
                .id(notice.getId())
                .userId(notice.getUser().getId())
                .userName(notice.getUser().getUsername())
                .title(notice.getTitle())
                .body(notice.getBody())
                .fileNames(fileNames)
                .build();
    }
}
