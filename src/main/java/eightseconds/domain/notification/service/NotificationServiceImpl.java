package eightseconds.domain.notification.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.notification.dto.NotificationListResponse;
import eightseconds.domain.notification.entity.Notification;
import eightseconds.domain.notification.repository.NotificationRepository;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import eightseconds.infra.firebase.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserService userService;

    @Override
    public void sendMessageToBidder(PriceSuggestion priceSuggestion) throws IOException {
        Notification notification = Notification.toEntityFromPriceSuggestion(priceSuggestion);
        notificationRepository.save(notification);
        firebaseCloudMessageService.sendMessageTo(notification.getTargetToken(),
                notification.getENotificationCategory().getValue(), notification.getMessage());
    }

    @Override
    public void sendMessageToSeller(Item item) throws IOException {
        Notification notification = Notification.toEntityFromItem(item);
        notificationRepository.save(notification);
        firebaseCloudMessageService.sendMessageTo(notification.getTargetToken(),
                notification.getENotificationCategory().getValue(), notification.getMessage());
    }

    @Override
    public PaginationDto<List<NotificationListResponse>> getAllNotifications(Pageable pageable, Long userId) {
        userService.validateUserId(userId);
        Page<Notification> page = notificationRepository.findAllByUserId(pageable, userId);
        List<NotificationListResponse> data = page.get().map(NotificationListResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

}
