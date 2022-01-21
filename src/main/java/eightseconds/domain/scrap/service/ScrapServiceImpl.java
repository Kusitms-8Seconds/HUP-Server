package eightseconds.domain.scrap.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.NotFoundItemException;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.scrap.constant.ScrapConstants.EScrapServiceImpl;
import eightseconds.domain.scrap.dto.*;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.scrap.exception.AlreadyScrapException;
import eightseconds.domain.scrap.exception.NotExistingScrapOfUserException;
import eightseconds.domain.scrap.repository.ScrapRepository;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScrapServiceImpl implements ScrapService{

    private final ItemService itemService;
    private final UserService userService;
    private final ScrapRepository scrapRepository;

    @Override
    public ScrapRegisterResponse saveScrap(ScrapRegisterRequest scrapRegisterRequest) {

        User user = userService.getUserByUserId(scrapRegisterRequest.getUserId());
        Item item = itemService.getItemByItemId(scrapRegisterRequest.getItemId());
        validationExistingScrap(user, item.getId());
        Scrap scrap = Scrap.builder()
                .user(user)
                .item(item)
                .build();
        return ScrapRegisterResponse.from(scrapRepository.save(scrap));
    }

    @Override
    public void deleteScrap(Long scrapId) {
        Scrap scrap = validationIsExistingScrap(scrapId);
        scrap.setUser(null);
        scrap.setItem(null);
        scrapRepository.deleteById(scrap.getId());
    }

    @Override
    public PaginationDto<List<ScrapDetailsResponse>> getAllScrapsByUserId(Pageable pageable, Long userId) {
        userService.validateUserId(userId);
        validationExistingScrapByUserId(pageable, userId);
        Page<Scrap> page = scrapRepository.findAllByUserId(pageable, userId);
        List<ScrapDetailsResponse> data = page.get().map(ScrapDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public Long getAllScrapsByItemIdQuantity(Long id) {
        Long itemCountByItemId = scrapRepository.findItemCountByItemId(id);
        return itemCountByItemId;
    }

    @Override
    public ScrapCheckedResponse isCheckedScrap(ScrapCheckedRequest scrapCheckedRequest) {
        Optional<Scrap> scrap = scrapRepository.findByUserIdAndItemId(scrapCheckedRequest.getUserId(), scrapCheckedRequest.getItemId());
        if(scrap.isEmpty()){
            return ScrapCheckedResponse.from(false, null); }
        else
            return ScrapCheckedResponse.from(true, scrap.get().getId());
    }


    /**
     * validation
     */

    private Scrap validationIsExistingScrap(Long deleteScrapId) {
        return scrapRepository.findById(deleteScrapId)
                .stream()
                .findAny()
                .orElseThrow(() -> new NotFoundItemException("존재하지 않는 스크랩입니다."));
    }

    private void validationExistingScrap(User user, Long itemId) {
        if (!scrapRepository.findAll().isEmpty()) {
            List<Scrap> scraps = scrapRepository.findAllByUserId(user.getId());
            for (Scrap scrap : scraps) {
                if(scrap.getItem().getId() == itemId){
                    throw new AlreadyScrapException(EScrapServiceImpl.eAlreadyScrapExceptionMessage.getValue()); } } }
    }

    private void validationExistingScrapByUserId(Pageable pageable, Long userId) {
        if (scrapRepository.findAllByUserId(pageable, userId).isEmpty()) {
            throw new NotExistingScrapOfUserException(EScrapServiceImpl.eNotExistingScrapOfUserExceptionMessage.getValue());
        }
    }
}
