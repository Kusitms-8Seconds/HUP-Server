package eightseconds.domain.scrap.service;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.exception.NotFoundItemException;
import eightseconds.domain.item.exception.NotFoundItemForCategoryException;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.scrap.constant.ScrapConstants;
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

        User user = this.userService.getUserByUserId(scrapRegisterRequest.getUserId());
        Item item = this.itemService.getItemByItemId(scrapRegisterRequest.getItemId());
        validateExistingScrap(user, item.getId());
        Scrap scrap = Scrap.builder()
                .user(user)
                .item(item)
                .build();
        return ScrapRegisterResponse.from(this.scrapRepository.save(scrap));
    }

    @Override
    public void deleteScrap(Long scrapId) {
        Scrap scrap = validateIsExistingScrap(scrapId);
        scrap.setUser(null);
        scrap.setItem(null);
        this.scrapRepository.deleteById(scrap.getId());
    }

    @Override
    public PaginationDto<List<ScrapDetailsResponse>> getAllScrapsByUserId(Pageable pageable, Long userId) {
        this.userService.validateUserId(userId);
        validateExistingScrapByUserId(pageable, userId);
        Page<Scrap> page = this.scrapRepository.findAllByUserId(pageable, userId);
        List<ScrapDetailsResponse> data = page.get().map(ScrapDetailsResponse::from).collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public ScrapCountResponse getAllScraps(Long id) {
        this.itemService.validateItemId(id);
        return ScrapCountResponse.from(this.scrapRepository.findItemCountByItemId(id));
    }

    @Override
    public ScrapCheckedResponse isCheckedScrap(ScrapCheckedRequest scrapCheckedRequest) {
        Optional<Scrap> scrap = this.scrapRepository.findByUserIdAndItemId(scrapCheckedRequest.getUserId(), scrapCheckedRequest.getItemId());
        if(scrap.isEmpty()){return ScrapCheckedResponse.from(false, null); }
        else return ScrapCheckedResponse.from(true, scrap.get().getId());
    }

    /**
     * validation
     */

    private Scrap validateIsExistingScrap(Long deleteScrapId) {
        return this.scrapRepository.findById(deleteScrapId)
                .orElseThrow(() -> new NotFoundItemException());
    }

    private void validateExistingScrap(User user, Long itemId) {
        if (!this.scrapRepository.findAll().isEmpty()) {
            List<Scrap> scraps = this.scrapRepository.findAllByUserId(user.getId());
            for (Scrap scrap : scraps) {
                if(scrap.getItem().getId() == itemId){
                    throw new AlreadyScrapException(); } } }
    }

    private void validateExistingScrapByUserId(Pageable pageable, Long userId) {
        if (this.scrapRepository.findAllByUserId(pageable, userId).isEmpty()) {
            throw new NotExistingScrapOfUserException();
        }
    }
}
