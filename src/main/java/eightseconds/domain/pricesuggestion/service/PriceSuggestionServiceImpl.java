package eightseconds.domain.pricesuggestion.service;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.NotOnGoingException;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants;
import eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.EPriceSuggestionServiceImpl;
import eightseconds.domain.pricesuggestion.dto.*;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.exception.*;
import eightseconds.domain.pricesuggestion.repository.PriceSuggestionRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import eightseconds.infra.email.constant.EmailConstants;
import eightseconds.infra.email.exception.ExpiredAuthCodeTimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PriceSuggestionServiceImpl implements PriceSuggestionService{

    private final PriceSuggestionRepository priceSuggestionRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    public PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByItemId(Pageable pageable, Long itemId) {
        this.itemService.validateItemId(itemId);
        this.itemService.validateSoldStatusByItemId(itemId);
        Page<PriceSuggestion> page = this.priceSuggestionRepository.findAllByItemId(pageable, itemId);
        List<PriceSuggestionListResponse> data = page.get().map(PriceSuggestionListResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    @Transactional
    public PriceSuggestionResponse priceSuggestionItem(PriceSuggestionRequest priceSuggestionRequest) {

        Long userId = priceSuggestionRequest.getUserId();
        Long itemId = priceSuggestionRequest.getItemId();
        int suggestionPrice = priceSuggestionRequest.getSuggestionPrice();

        PriceSuggestion resultPriceSuggestion;
        User user = this.userService.validateUserId(userId);
        Item item = this.itemService.validateItemId(itemId);
        validatePriceSuggestionsItemId(itemId);
        validateInitPrice(itemId, suggestionPrice);
        validatePriceSuggestionPrice(itemId, suggestionPrice);
        validateRegisteredItemByUser(item.getUser().getId(), userId);
        validateAuctionClosingTime(item);

        Optional<PriceSuggestion> priceSuggestion = this.priceSuggestionRepository.getByUserIdAndItemId(userId, itemId);
        ItemConstants.EItemSoldStatus soldStatus = this.itemService.getItem(itemId).getSoldStatus();
        if(!priceSuggestion.isEmpty()){
            validatePrice(priceSuggestion.get().getSuggestionPrice(), suggestionPrice);
            priceSuggestion.get().setSuggestionPrice(suggestionPrice);
            resultPriceSuggestion = priceSuggestion.get(); }

        else{ PriceSuggestion entity = PriceSuggestion.toEntity(user, item, suggestionPrice);
            this.priceSuggestionRepository.save(entity);
            resultPriceSuggestion = entity; }

        int maximumPrice = getMaximumPrice(itemId).getMaximumPrice();
        int participants = getParticipants(itemId).getParticipantsCount();

        return PriceSuggestionResponse.from(item, user, resultPriceSuggestion, maximumPrice, participants, soldStatus);
    }


    @Override
    public MaximumPriceResponse getMaximumPrice(Long itemId) {
        this.itemService.validateItemId(itemId);
        this.itemService.validateSoldStatusByItemId(itemId);
        Optional<PriceSuggestion> priceSuggestion = this.priceSuggestionRepository.getMaximumPriceByItemId(itemId);
        if(priceSuggestion.isEmpty()) return MaximumPriceResponse.from(0);
        else return MaximumPriceResponse.from(priceSuggestion.get().getSuggestionPrice());
    }

    @Override
    public ParticipantsResponse getParticipants(Long itemId) {
        this.itemService.validateItemId(itemId);
        this.itemService.validateSoldStatusByItemId(itemId);
        return ParticipantsResponse.from(this.priceSuggestionRepository.findPriceSuggestionCountByItemId(itemId));
    }

    @Override
    public PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByUserId(Pageable pageable, Long userId) {
        this.userService.validateUserId(userId);
        Page<PriceSuggestion> page = this.priceSuggestionRepository.findAllByUserId(pageable, userId);
        List<PriceSuggestionListResponse> data = page.get().map(PriceSuggestionListResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public BidderResponse getBidder(Long itemId) {
        Optional<PriceSuggestion> priceSuggestion = this.priceSuggestionRepository.getMaximumPriceByItemId(itemId);
        return BidderResponse.from(priceSuggestion.get().getUser().getUsername());
    }

    public void deletePriceSuggestion(Long priceSuggestionId) {
        validatePriceSuggestionId(priceSuggestionId);
        this.priceSuggestionRepository.deleteById(priceSuggestionId);
    }

    /**
     * validate
     */

    private void validatePriceSuggestionPrice(Long itemId, int suggestionPrice) {
        Optional<PriceSuggestion> maximumPriceByItemId = this.priceSuggestionRepository.getMaximumPriceByItemId(itemId);
        if(!maximumPriceByItemId.isEmpty()){
            if(maximumPriceByItemId.get().getSuggestionPrice() >= suggestionPrice){
                throw new PriorPriceSuggestionException(); }
        }
    }

    private void validateOnGoingItem(Item item) {
        if(!item.getSoldStatus().equals(ItemConstants.EItemSoldStatus.eOnGoing)){
            throw new NotOnGoingException();
        }
    }

    private void validateAcceptStatusPriceSuggestion(List<PriceSuggestion> priceSuggestions) {
        if (priceSuggestions.size() != EPriceSuggestionServiceImpl.eZero.getSize()) {
            for (PriceSuggestion priceSuggestion : priceSuggestions) {
                if(priceSuggestion.isAcceptState() == true){
                    throw new AlreadySoldOutException(); } } }
    }

    private void validatePrice(int priorSuggestionPrice, int suggestionPrice) {
        if(priorSuggestionPrice >= suggestionPrice){
            throw new PriorPriceSuggestionException(); }
    }

    private void validatePriceSuggestionsItemId(Long itemId) {
        List<PriceSuggestion> priceSuggestions= this.priceSuggestionRepository.findAllByItemId(itemId);
        Item item = this.itemService.getItemByItemId(itemId);
        validateOnGoingItem(item);
        validateAcceptStatusPriceSuggestion(priceSuggestions);
    }

    private void validatePriceSuggestionId(Long priceSuggestionId) {
        this.priceSuggestionRepository.findById(priceSuggestionId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundPriceSuggestionException());
    }

    private void validateRegisteredItemByUser(Long registeredUserId, Long userId) {
        if (registeredUserId.equals(userId)) {
            throw new SameUserIdException();}
    }

    private void validateInitPrice(Long itemId, int suggestionPrice) {
        int initPrice = this.itemService.getItemByItemId(itemId).getInitPrice();
        if (suggestionPrice < initPrice) {
            throw new InitPriceException();}
    }

    private void validateAuctionClosingTime(Item item) {
        LocalDateTime closingDate = item.getAuctionClosingDate();
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (closingDate.isBefore(currentDateTime)) throw new AuctionClosingTimeException();
    }

}
