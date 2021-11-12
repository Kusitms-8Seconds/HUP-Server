package eightseconds.domain.pricesuggestion.service;

import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PriceSuggestionService {

    Page<PriceSuggestion> getAllPriceSuggestionsItemId(Pageable pageable, Long itemId);
    int getMaximumPrice(Long itemId);
    void validationPriceSuggestionsItemId(Long itemId);
    PriceSuggestion priceSuggestionItem(Long userId, Long itemId, int suggestionPrice);
    int getParticipants(Long itemId);

}
