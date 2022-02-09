package eightseconds.domain.notification.dto;

import eightseconds.domain.notification.entity.Notification;
import eightseconds.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "해당 유저의 알림내역을 조회하기 위한 응답 객체")
public class NotificationListResponse {

    private Long id;
    private String username;
    private String message;
    private String eNotificationCategory;

    public static NotificationListResponse from(Notification notification) {
        User user = notification.getUser();

        return NotificationListResponse.builder()
                .id(notification.getId())
                .username(user.getUsername())
                .message(notification.getMessage())
                .eNotificationCategory(notification.getENotificationCategory().getValue())
                .build();
    }
}
