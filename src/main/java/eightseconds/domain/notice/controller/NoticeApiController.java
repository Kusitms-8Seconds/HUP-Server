package eightseconds.domain.notice.controller;

import eightseconds.domain.notice.constant.NoticeConstants.ENoticeApiController;
import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ENoticeApiController.eLocationIdPath.getValue())
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(EntityModel.of(noticeService.createNotice(userId, title, body, files))
                .add(linkTo(methodOn(this.getClass()).createNotice(files, userId, title, body)).withSelfRel()));
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "공지사항 삭제", notes = "공지사항을 삭제합니다.")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<EntityModel<?>> deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.noContent().build();
    }

}
