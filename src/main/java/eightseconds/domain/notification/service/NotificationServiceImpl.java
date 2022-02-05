package eightseconds.domain.notification.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.notification.entity.Notification;
import eightseconds.domain.notification.repository.NotificationRepository;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.infra.firebase.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

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
}
