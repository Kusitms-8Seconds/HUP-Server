package eightseconds.domain.notification.controller;

import eightseconds.domain.notification.dto.NotificationListResponse;
import eightseconds.domain.notification.service.NotificationService;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
@Api(tags = "Notification API")
public class NotificationApiController {

    private final NotificationService notificationService;

    @ApiOperation(value = "해당 유저의 모든 알림 조회", notes = "해당 유저의 모든 알림 내역을 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<EntityModel<PaginationDto<List<NotificationListResponse>>>> getAllNotifications(@PageableDefault Pageable pageable, @PathVariable Long userId) {
        return ResponseEntity.ok(EntityModel.of(notificationService.getAllNotifications(pageable, userId)));
    }

    @ApiOperation(value = "해당 알림의 상세한 정보 조회", notes = "해당 알림의 상세한 정보를 조회합니다.")
    @GetMapping("/{notificationId}")
    public ResponseEntity<EntityModel<PaginationDto<List<NotificationListResponse>>>> getNotification(@PageableDefault Pageable pageable, @PathVariable Long userId) {
        return ResponseEntity.ok(EntityModel.of(notificationService.getAllNotifications(pageable, userId)));
    }

}
