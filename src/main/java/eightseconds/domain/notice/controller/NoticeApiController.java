package eightseconds.domain.notice.controller;

import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notices")
public class NoticeApiController {

    private final NoticeService noticeService;

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "공지사항 등록", notes = "공지사항을 등록합니다.")
    @PostMapping
    public ResponseEntity<EntityModel<NoticeResponse>> createNotice(@Nullable @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                                    @RequestPart(value = "userId") Long userId,
                                                                    @RequestPart(value = "title", required = false) String title,
                                                                    @RequestPart(value = "body", required = false) String body) throws IOException {
        return ResponseEntity.ok(EntityModel.of(noticeService.createNotice(userId, title, body, files)));
    }
}
