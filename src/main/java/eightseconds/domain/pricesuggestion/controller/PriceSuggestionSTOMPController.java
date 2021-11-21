package eightseconds.domain.pricesuggestion.controller;

import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.dto.PriceSuggestionRequest;
import eightseconds.domain.pricesuggestion.dto.PriceSuggestionResponse;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.service.PriceSuggestionService;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class PriceSuggestionSTOMPController {

    private final PriceSuggestionService priceSuggestionService;
    private final ItemService itemService;
    private final UserService userService;

    @MessageMapping("/priceSuggestion")
    @SendTo("/topic/priceSuggestion")
    @Transactional
    public PriceSuggestionResponse priceSuggestion(PriceSuggestionRequest priceSuggestionRequest) throws Exception {
        Thread.sleep(100); // delay
        Long itemId = priceSuggestionRequest.getItemId();
        int suggestionPrice = priceSuggestionRequest.getSuggestionPrice();
        Long userId = priceSuggestionRequest.getUserId();
        priceSuggestionService.validationPriceSuggestionsItemId(itemId);
        PriceSuggestion priceSuggestion = priceSuggestionService.priceSuggestionItem(userId, itemId, suggestionPrice);
        Item item = itemService.getItem(itemId);
        User user = userService.getUserByUserId(userId);
        int maximumPrice = priceSuggestionService.getMaximumPrice(itemId);
        int participants = priceSuggestionService.getParticipants(itemId);
        EItemSoldStatus soldStatus = itemService.getItem(itemId).getSoldStatus();
        return PriceSuggestionResponse.from(item, user, priceSuggestion, maximumPrice, participants, soldStatus);
    }
}
