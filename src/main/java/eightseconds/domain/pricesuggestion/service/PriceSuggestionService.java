package eightseconds.domain.pricesuggestion.service;

import eightseconds.domain.pricesuggestion.dto.BidderResponse;
import eightseconds.domain.pricesuggestion.dto.PriceSuggestionListResponse;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PriceSuggestionService {

    PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByItemId(Pageable pageable, Long itemId);
    int getMaximumPrice(Long itemId);
    void validationPriceSuggestionsItemId(Long itemId);
    PriceSuggestion priceSuggestionItem(Long userId, Long itemId, int suggestionPrice);
    int getParticipants(Long itemId);
    PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByUserId(Pageable pageable, Long userId);
    BidderResponse getBidder(Long itemId);
}
