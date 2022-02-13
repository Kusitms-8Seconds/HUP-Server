package eightseconds.domain.pricesuggestion.service;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.NotOnGoingException;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.constant.PriceSuggestionConstants.EPriceSuggestionServiceImpl;
import eightseconds.domain.pricesuggestion.dto.*;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.exception.AlreadySoldOutException;
import eightseconds.domain.pricesuggestion.exception.NotFoundPriceSuggestionException;
import eightseconds.domain.pricesuggestion.exception.PriorPriceSuggestionException;
import eightseconds.domain.pricesuggestion.exception.SameUserIdException;
import eightseconds.domain.pricesuggestion.repository.PriceSuggestionRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        itemService.validateItemId(itemId);
        itemService.validateSoldStatusByItemId(itemId);
        Page<PriceSuggestion> page = priceSuggestionRepository.findAllByItemId(pageable, itemId);
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
        User user = userService.validateUserId(userId);
        Item item = itemService.validateItemId(itemId);
        validatePriceSuggestionsItemId(itemId);
        validateRegisteredItemByUser(item.getUser().getId(), userId);
        Optional<PriceSuggestion> priceSuggestion = priceSuggestionRepository.getByUserIdAndItemId(userId, itemId);
        int maximumPrice = getMaximumPrice(itemId).getMaximumPrice();
        int participants = getParticipants(itemId).getParticipantsCount();
        ItemConstants.EItemSoldStatus soldStatus = itemService.getItem(itemId).getSoldStatus();

        if(!priceSuggestion.isEmpty()){
            validatePrice(priceSuggestion.get().getSuggestionPrice(), suggestionPrice);
            priceSuggestion.get().setSuggestionPrice(suggestionPrice);
            resultPriceSuggestion = priceSuggestion.get(); }

        else{ PriceSuggestion entity = PriceSuggestion.toEntity(user, item, suggestionPrice);
            priceSuggestionRepository.save(entity);
            resultPriceSuggestion = entity; }

        return PriceSuggestionResponse.from(item, user, resultPriceSuggestion, maximumPrice, participants, soldStatus);
    }

    @Override
    public MaximumPriceResponse getMaximumPrice(Long itemId) {
        itemService.validateItemId(itemId);
        itemService.validateSoldStatusByItemId(itemId);
        Optional<PriceSuggestion> priceSuggestion = priceSuggestionRepository.getMaximumPriceByItemId(itemId);
        if(priceSuggestion.isEmpty()){ return MaximumPriceResponse.from(0); }
        else{ return MaximumPriceResponse.from(priceSuggestionRepository.getMaximumPriceByItemId(itemId).get().getSuggestionPrice());}
    }

    @Override
    public ParticipantsResponse getParticipants(Long itemId) {
        itemService.validateItemId(itemId);
        itemService.validateSoldStatusByItemId(itemId);
        return ParticipantsResponse.from(priceSuggestionRepository.findPriceSuggestionCountByItemId(itemId));
    }

    @Override
    public PaginationDto<List<PriceSuggestionListResponse>> getAllPriceSuggestionsByUserId(Pageable pageable, Long userId) {
        userService.validateUserId(userId);
        Page<PriceSuggestion> page = priceSuggestionRepository.findAllByUserId(pageable, userId);
        List<PriceSuggestionListResponse> data = page.get().map(PriceSuggestionListResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public BidderResponse getBidder(Long itemId) {
        Optional<PriceSuggestion> priceSuggestion = priceSuggestionRepository.getMaximumPriceByItemId(itemId);
        return BidderResponse.from(priceSuggestion.get().getUser().getUsername());
    }

    public void deletePriceSuggestion(Long priceSuggestionId) {
        validatePriceSuggestionId(priceSuggestionId);
        priceSuggestionRepository.deleteById(priceSuggestionId);
    }

    /**
     * validate
     */

    private void validateOnGoingItem(Item item) {
        if(!item.getSoldStatus().equals(ItemConstants.EItemSoldStatus.eOnGoing)){
            throw new NotOnGoingException(EPriceSuggestionServiceImpl.eNotOnGoingExceptionMessage.getValue());
        }
    }

    private void validateAcceptStatusPriceSuggestion(List<PriceSuggestion> priceSuggestions) {
        if (priceSuggestions.size() != EPriceSuggestionServiceImpl.eZero.getSize()) {
            for (PriceSuggestion priceSuggestion : priceSuggestions) {
                if(priceSuggestion.isAcceptState() == true){
                    throw new AlreadySoldOutException(EPriceSuggestionServiceImpl.eAlreadySoldOutExceptionMessage.getValue()); } } }
    }

    private void validatePrice(int priorSuggestionPrice, int suggestionPrice) {
        if(priorSuggestionPrice >= suggestionPrice){
            throw new PriorPriceSuggestionException(EPriceSuggestionServiceImpl.ePriorPriceSuggestionExceptionMessage.getValue()); }
    }

    @Override
    public void validatePriceSuggestionsItemId(Long itemId) {
        List<PriceSuggestion> priceSuggestions= priceSuggestionRepository.findAllByItemId(itemId);
        Item item = itemService.getItemByItemId(itemId);
        validateOnGoingItem(item);
        validateAcceptStatusPriceSuggestion(priceSuggestions);
    }

    private void validatePriceSuggestionId(Long priceSuggestionId) {
        priceSuggestionRepository.findById(priceSuggestionId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundPriceSuggestionException(EPriceSuggestionServiceImpl.eNotFoundPriceSuggestionExceptionMessage.getValue()));
    }

    private void validateRegisteredItemByUser(Long registeredUserId, Long userId) {
        if (registeredUserId.equals(userId)) {
            throw new SameUserIdException(EPriceSuggestionServiceImpl.eSameUserIdExceptionMessage.getValue());}
    }

}
