package eightseconds.domain.pricesuggestion.controller;

import eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.EPriceSuggestionApiController;
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

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/priceSuggestions")
public class PriceSuggestionApiController {

    private final PriceSuggestionService priceSuggestionService;

    @ApiOperation(value = "해당 상품의 모든 입찰내역 조회", notes = "해당 상품의 모든 입찰내역을 조회합니다.")
    @GetMapping("{itemId}")
    public ResponseEntity<EntityModel<PaginationDto<List<PriceSuggestionListResponse>>>> getAllPriceSuggestions(@PageableDefault Pageable pageable,
                                                                                                         @PathVariable Long itemId) {

        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getAllPriceSuggestionsByItemId(pageable, itemId))
                .add(linkTo(methodOn(this.getClass()).getAllPriceSuggestions(pageable, itemId)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getMaximumPrice(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getParticipants(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getBidderName(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue())));
    }

    @ApiOperation(value = "해당 상품의 최고입찰 가격 조회", notes = "해당 상품의 최고입찰가격을 조회합니다.")
    @GetMapping("/maximumPrice/{itemId}")
    public ResponseEntity<EntityModel<MaximumPriceResponse>> getMaximumPrice(@PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getMaximumPrice(itemId))
                .add(linkTo(methodOn(this.getClass()).getMaximumPrice(itemId)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getAllPriceSuggestions(Pageable.ofSize(EPriceSuggestionApiController.ePageableOne.getPage()), itemId))
                        .withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getParticipants(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getBidderName(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue())));
    }

    @ApiOperation(value = "해당 상품의 참여자수 조회", notes = "해당 상품의 참여자수를 조회합니다.")
    @GetMapping("/participants/{itemId}")
    public ResponseEntity<EntityModel<ParticipantsResponse>> getParticipants(@PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getParticipants(itemId))
                .add(linkTo(methodOn(this.getClass()).getParticipants(itemId)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getAllPriceSuggestions(Pageable.ofSize(EPriceSuggestionApiController.ePageableOne.getPage()), itemId))
                        .withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getMaximumPrice(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getBidderName(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue())));
    }

    @ApiOperation(value = "해당 상품의 최고입찰 유저 조회", notes = "해당 상품의 최고입찰을 한 유저를 조회합니다.")
    @GetMapping("/bidders/{itemId}")
    public ResponseEntity<EntityModel<BidderResponse>> getBidderName(@PathVariable Long itemId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getBidder(itemId))
                .add(linkTo(methodOn(this.getClass()).getBidderName(itemId)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getAllPriceSuggestions(Pageable.ofSize(EPriceSuggestionApiController.ePageableOne.getPage()), itemId))
                        .withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getMaximumPrice(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).getParticipants(itemId)).withRel(EPriceSuggestionApiController.eGetMethod.getValue())));
    }

    @ApiOperation(value = "해당 유저의 경매 참여내역 조회", notes = "해당 유저의 경매 참여내역을 조회합니다.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<EntityModel<PaginationDto<List<PriceSuggestionListResponse>>>> getAllPriceSuggestionsOfUser(@PageableDefault Pageable pageable, @PathVariable Long userId) {
        return ResponseEntity.ok(EntityModel.of(priceSuggestionService.getAllPriceSuggestionsByUserId(pageable, userId)));
    }

    // 입찰 Test용도
    @PostMapping("/priceSuggestionTest")
    public ResponseEntity<PriceSuggestionResponse> priceSuggestion(@Valid @RequestBody PriceSuggestionRequest priceSuggestionRequest) throws Exception {
        return ResponseEntity.ok(priceSuggestionService.priceSuggestionItem(priceSuggestionRequest));
    }

}
