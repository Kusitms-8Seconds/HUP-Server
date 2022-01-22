package eightseconds.domain.pricesuggestion.controller;

import eightseconds.domain.pricesuggestion.dto.*;
import eightseconds.domain.pricesuggestion.service.PriceSuggestionService;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/priceSuggestions")
public class PriceSuggestionApiController {

    private final PriceSuggestionService priceSuggestionService;

    @ApiOperation(value = "해당 상품의 모든 입찰내역 조회", notes = "해당 상품의 모든 입찰내역을 조회합니다.")
    @GetMapping("{itemId}")
    public ResponseEntity<EntityModel<PaginationDto<List<PriceSuggestionListResponse>>>> findAllByItemId(@PageableDefault Pageable pageable,
                                                                                                         @PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getAllPriceSuggestionsByItemId(pageable, itemId)));
    }

    @ApiOperation(value = "해당 상품의 최고입찰 가격 조회", notes = "해당 상품의 최고입찰가격을 조회합니다.")
    @GetMapping("/maximumPrice/{itemId}")
    public ResponseEntity<EntityModel<MaximumPriceResponse>> findMaximumPrice(@PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getMaximumPrice(itemId)));
    }

    @ApiOperation(value = "해당 상품의 참여자수 조회", notes = "해당 상품의 참여자수를 조회합니다.")
    @GetMapping("/participants/{itemId}")
    public ResponseEntity<EntityModel<ParticipantsResponse>> findParticipants(@PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getParticipants(itemId)));
    }

    @ApiOperation(value = "해당 유저의 경매 참여내역 조회", notes = "해당 유저의 경매 참여내역을 조회합니다.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<EntityModel<PaginationDto<List<PriceSuggestionListResponse>>>> findAllByUserId(@PageableDefault Pageable pageable, @PathVariable Long userId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getAllPriceSuggestionsByUserId(pageable, userId)));
    }

    @ApiOperation(value = "해당 상품의 최고입찰 유저 조회", notes = "해당 상품의 최고입찰을 한 유저를 조회합니다.")
    @GetMapping("/bidders/{itemId}")
    public ResponseEntity<EntityModel<BidderResponse>> getBidder(@PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getBidder(itemId)));
    }

    // 입찰 Test용도임 이걸로쓰면 안됨!
    @PostMapping("/priceSuggestionTest")
    public ResponseEntity<PriceSuggestionResponse> priceSuggestion(@RequestBody PriceSuggestionRequest priceSuggestionRequest) throws Exception {
//        Long itemId = priceSuggestionRequest.getItemId();
//        int suggestionPrice = priceSuggestionRequest.getSuggestionPrice();
//        Long userId = priceSuggestionRequest.getUserId();
//        priceSuggestionService.validationPriceSuggestionsItemId(itemId);
//        PriceSuggestion priceSuggestion = priceSuggestionService.priceSuggestionItem(userId, itemId, suggestionPrice);
//        Item item = itemService.getItemByItemId(itemId);
//        User user = userService.getUserByUserId(userId);
//        int maximumPrice = priceSuggestionService.getMaximumPrice(itemId);
//        int participants = priceSuggestionService.getParticipants(itemId);
//        ItemConstants.EItemSoldStatus soldStatus = itemService.getItem(itemId).getSoldStatus();
//        return ResponseEntity.ok(PriceSuggestionResponse.from(item, user, priceSuggestion, maximumPrice, participants, soldStatus));
        return null;
    }

}
