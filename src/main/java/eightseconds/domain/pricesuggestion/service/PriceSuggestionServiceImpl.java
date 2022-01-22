package eightseconds.domain.pricesuggestion.service;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.NotOnGoingException;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.dto.BidderResponse;
import eightseconds.domain.pricesuggestion.dto.MaximumPriceResponse;
import eightseconds.domain.pricesuggestion.dto.ParticipantsResponse;
import eightseconds.domain.pricesuggestion.dto.PriceSuggestionListResponse;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.pricesuggestion.exception.AlreadySoldOutException;
import eightseconds.domain.pricesuggestion.exception.PriorPriceSuggestionException;
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
    public PriceSuggestion priceSuggestionItem(Long userId, Long itemId, int suggestionPrice) {
        Optional<PriceSuggestion> priceSuggestion = priceSuggestionRepository.getByUserIdAndItemId(userId, itemId);
        User user = userService.getUserByUserId(userId);
        Item item = itemService.getItemByItemId(itemId);
        if(!priceSuggestion.isEmpty()){
            validationPrice(priceSuggestion.get().getSuggestionPrice(), suggestionPrice);
            priceSuggestion.get().setSuggestionPrice(suggestionPrice);
            return priceSuggestion.get(); }
        else{
            PriceSuggestion entity = PriceSuggestion.toEntity(user, item, suggestionPrice);
            priceSuggestionRepository.save(entity);
            return entity; }
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


    /**
     * validation
     */

    private void validationOnGoingItem(Item item) {
        if(!item.getSoldStatus().equals(ItemConstants.EItemSoldStatus.eOnGoing)){
            throw new NotOnGoingException("경매 진행중인 상품이 아닙니다.");
        }
    }

    private void validationAcceptStatusPriceSuggestion(List<PriceSuggestion> priceSuggestions) {
        if (priceSuggestions.size() != 0) {
            for (PriceSuggestion priceSuggestion : priceSuggestions) {
                if(priceSuggestion.isAcceptState() == true){
                    throw new AlreadySoldOutException("이미 팔린 상품입니다."); } } }
    }

    private void validationPrice(int priorSuggestionPrice, int suggestionPrice) {
        if(priorSuggestionPrice >= suggestionPrice){
            throw new PriorPriceSuggestionException("이전의 입찰가격이 지금의 입찰보다 높거나 같습니다."); }
    }

    @Override
    public void validationPriceSuggestionsItemId(Long itemId) {
        List<PriceSuggestion> priceSuggestions= priceSuggestionRepository.findAllByItemId(itemId);
        Item item = itemService.getItemByItemId(itemId);
        validationOnGoingItem(item);
        validationAcceptStatusPriceSuggestion(priceSuggestions);
    }

}
