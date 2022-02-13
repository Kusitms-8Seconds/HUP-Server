package eightseconds.domain.pricesuggestion.service;

import eightseconds.domain.pricesuggestion.dto.*;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.global.dto.PaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PriceSuggestionService {

    PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByItemId(Pageable pageable, Long itemId);
    MaximumPriceResponse getMaximumPrice(Long itemId);
    void validatePriceSuggestionsItemId(Long itemId);
    PriceSuggestionResponse priceSuggestionItem(PriceSuggestionRequest priceSuggestionRequest);
    ParticipantsResponse getParticipants(Long itemId);
    PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByUserId(Pageable pageable, Long userId);
    BidderResponse getBidder(Long itemId);
    void deletePriceSuggestion(Long priceSuggestionId);
}
