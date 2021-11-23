package eightseconds.domain.pricesuggestion.controller;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.dto.*;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.service.PriceSuggestionService;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/priceSuggestion")
public class PriceSuggestionApiController {

    private final ItemService itemService;
    private final PriceSuggestionService priceSuggestionService;
    private final UserService userService;

    @GetMapping("/list/item/{itemId}")
    public ResponseEntity<PaginationDto<List<PriceSuggestionListResponse>>> findAllByItemId(@PageableDefault Pageable pageable, @PathVariable Long itemId) {
        itemService.validationItemId(itemId);
        itemService.validationSoldStatusByItemId(itemId);
        return ResponseEntity.ok(priceSuggestionService.getAllPriceSuggestionsByItemId(pageable, itemId));
    }

    @GetMapping("/maximumPrice/{itemId}")
    public ResponseEntity<MaximumPriceResponse> findMaximumPrice(@PathVariable Long itemId) {
        itemService.validationItemId(itemId);
        itemService.validationSoldStatusByItemId(itemId);
        int maximumPrice = priceSuggestionService.getMaximumPrice(itemId);
        return ResponseEntity.ok(MaximumPriceResponse.from(maximumPrice));
    }

    @GetMapping("/participant/{itemId}")
    public ResponseEntity<ParticipantsResponse> findParticipants(@PathVariable Long itemId) {
        itemService.validationItemId(itemId);
        itemService.validationSoldStatusByItemId(itemId);
        int participants = priceSuggestionService.getParticipants(itemId);
        return ResponseEntity.ok(ParticipantsResponse.from(participants));
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<PaginationDto<List<PriceSuggestionListResponse>>> findAllByUserId(@PageableDefault Pageable pageable, @PathVariable Long userId) {
        userService.validationUserId(userId);
        return ResponseEntity.ok(priceSuggestionService.getAllPriceSuggestionsByUserId(pageable, userId));
    }

    @GetMapping("/bidder/{itemId}")
    public ResponseEntity<BidderResponse> getBidder(@PathVariable Long itemId) {
        BidderResponse bidder = priceSuggestionService.getBidder(itemId);
        return ResponseEntity.ok(bidder);
    }

    // 입찰 Test용도임 이걸로쓰면 안됨!
    @PostMapping("/priceSuggestionTest")
    public ResponseEntity<PriceSuggestionResponse> priceSuggestion(@RequestBody PriceSuggestionRequest priceSuggestionRequest) throws Exception {
        Long itemId = priceSuggestionRequest.getItemId();
        int suggestionPrice = priceSuggestionRequest.getSuggestionPrice();
        Long userId = priceSuggestionRequest.getUserId();
        priceSuggestionService.validationPriceSuggestionsItemId(itemId);
        PriceSuggestion priceSuggestion = priceSuggestionService.priceSuggestionItem(userId, itemId, suggestionPrice);
        Item item = itemService.getItem(itemId);
        User user = userService.getUserByUserId(userId);
        int maximumPrice = priceSuggestionService.getMaximumPrice(itemId);
        int participants = priceSuggestionService.getParticipants(itemId);
        ItemConstants.EItemSoldStatus soldStatus = itemService.getItem(itemId).getSoldStatus();
        return ResponseEntity.ok(PriceSuggestionResponse.from(item, user, priceSuggestion, maximumPrice, participants, soldStatus));
    }

}
