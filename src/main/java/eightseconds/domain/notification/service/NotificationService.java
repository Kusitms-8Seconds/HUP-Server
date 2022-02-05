package eightseconds.domain.notification.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;

import java.io.IOException;

public interface NotificationService {
    void sendMessageToBidder(PriceSuggestion priceSuggestion) throws IOException;
    void sendMessageToSeller(Item item) throws IOException;

}
