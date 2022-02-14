package eightseconds.domain.notice.controller;

import eightseconds.domain.notice.constant.NoticeConstants.ENoticeApiController;
import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.dto.UpdateNoticeResponse;
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
                                                                    @RequestPart(value = "userId") String userId,
                                                                    @RequestPart(value = "title", required = false) String title,
                                                                    @RequestPart(value = "body", required = false) String body) throws IOException {
        NoticeResponse noticeResponse = noticeService.createNotice(userId, title, body, files);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ENoticeApiController.eLocationIdPath.getValue())
                .buildAndExpand(noticeResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(EntityModel.of(noticeResponse)
                .add(linkTo(methodOn(this.getClass()).createNotice(files, userId, title, body)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).deleteNotice(noticeResponse.getId())).withRel(ENoticeApiController.eDeleteMethod.getValue())));
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "공지사항 삭제", notes = "공지사항을 삭제합니다.")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<EntityModel<?>> deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "공지사항 수정", notes = "공지사항을 수정합니다.")
    @PutMapping
    public ResponseEntity<EntityModel<?>> putNotice(@Nullable @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       @RequestPart(value = "userId") String userId,
                                                       @RequestPart(value = "noticeId") String noticeId,
                                                       @RequestPart(value = "title", required = false) String title,
                                                       @RequestPart(value = "body", required = false) String body) throws IOException {
        UpdateNoticeResponse updateNoticeResponse = noticeService.updateNotice(userId, noticeId, title, body, files);
        return ResponseEntity.ok().body(EntityModel.of(updateNoticeResponse)
                .add(linkTo(methodOn(this.getClass()).putNotice(files, userId, noticeId, title, body)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).deleteNotice(Long.valueOf(noticeId))).withRel(ENoticeApiController.eDeleteMethod.getValue())));
    }

}
