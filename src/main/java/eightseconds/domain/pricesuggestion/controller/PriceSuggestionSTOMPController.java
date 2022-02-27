package eightseconds.domain.pricesuggestion.controller;

import eightseconds.domain.pricesuggestion.dto.PriceSuggestionRequest;
import eightseconds.domain.pricesuggestion.dto.PriceSuggestionResponse;
import eightseconds.domain.pricesuggestion.service.PriceSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class PriceSuggestionSTOMPController {

    private final PriceSuggestionService priceSuggestionService;

    @MessageMapping("/priceSuggestions")
    @SendTo("/sub/priceSuggestions")
    @Transactional
    public PriceSuggestionResponse priceSuggestion(PriceSuggestionRequest priceSuggestionRequest) throws Exception {
        return priceSuggestionService.priceSuggestionItem(priceSuggestionRequest);
    }
}
