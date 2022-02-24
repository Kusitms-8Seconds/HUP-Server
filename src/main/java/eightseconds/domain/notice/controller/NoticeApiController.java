package eightseconds.domain.notice.controller;

import eightseconds.domain.notice.constant.NoticeConstants.ENoticeApiController;
import eightseconds.domain.notice.dto.NoticeListResponse;
import eightseconds.domain.notice.dto.NoticeResponse;
import eightseconds.domain.notice.dto.UpdateNoticeResponse;
import eightseconds.domain.notice.service.NoticeService;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "Notice API")
public class NoticeApiController {

    private final NoticeService noticeService;

    @PreAuthorize("hasAnyRole('ADMIN')")
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
                .add(linkTo(methodOn(this.getClass()).deleteNotice(noticeResponse.getId())).withRel(ENoticeApiController.eDeleteMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).updateNotice(files, userId, String.valueOf(noticeResponse.getId()), title, body))
                        .withRel(ENoticeApiController.ePutMethod.getValue())));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "공지사항 삭제", notes = "공지사항을 삭제합니다.")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<EntityModel<?>> deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "공지사항 수정", notes = "공지사항을 수정합니다.")
    @PutMapping
    public ResponseEntity<EntityModel<?>> updateNotice(@Nullable @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       @RequestPart(value = "userId") String userId,
                                                       @RequestPart(value = "noticeId") String noticeId,
                                                       @RequestPart(value = "title", required = false) String title,
                                                       @RequestPart(value = "body", required = false) String body) throws IOException {
        UpdateNoticeResponse updateNoticeResponse = noticeService.updateNotice(userId, noticeId, title, body, files);
        return ResponseEntity.ok().body(EntityModel.of(updateNoticeResponse)
                .add(linkTo(methodOn(this.getClass()).updateNotice(files, userId, noticeId, title, body)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).createNotice(files, userId, title, body)).withRel(ENoticeApiController.ePostMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).deleteNotice(Long.valueOf(noticeId))).withRel(ENoticeApiController.eDeleteMethod.getValue())));
    }

    @ApiOperation(value = "공지사항 전체 목록 조회", notes = "공지사항 전체 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<EntityModel<PaginationDto<List<NoticeListResponse>>>> getAllNotices(@PageableDefault Pageable pageable) {
        PaginationDto<List<NoticeListResponse>> list = noticeService.getAllNotices(pageable);
        return ResponseEntity.ok().body(EntityModel.of(list)
                .add(linkTo(methodOn(this.getClass()).getAllNotices(pageable)).withSelfRel()));

    }

    @ApiOperation(value = "공지사항 상세 조회", notes = "공지사항 상세 정보를 조회합니다.")
    @GetMapping("/{noticeId}")
    public ResponseEntity<EntityModel<NoticeResponse>> getNotice(@PathVariable Long noticeId) {
        NoticeResponse noticeResponse = noticeService.getNotice(noticeId);
        return ResponseEntity.ok().body(EntityModel.of(noticeResponse));
    }

}
