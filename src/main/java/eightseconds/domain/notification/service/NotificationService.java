package eightseconds.domain.notification.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.notification.dto.NotificationListResponse;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface NotificationService {
    void sendMessageToBidder(PriceSuggestion priceSuggestion) throws IOException;
    void sendMessageToSeller(Item item) throws IOException;
    PaginationDto<List<NotificationListResponse>> getAllNotifications(Pageable pageable, Long userId);

}
