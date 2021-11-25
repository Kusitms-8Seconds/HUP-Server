package eightseconds.domain.scrap.service;

import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.repository.ItemRepository;
import eightseconds.domain.scrap.dto.ScrapCheckedRequest;
import eightseconds.domain.scrap.dto.ScrapCheckedResponse;
import eightseconds.domain.scrap.dto.ScrapDetailsResponse;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.scrap.repository.ScrapRepository;
import eightseconds.domain.user.entity.User;
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

    private final ScrapRepository scrapRepository;
    private final ItemRepository itemRepository;

    @Override
    public Scrap saveScrap(User user, Item item) {

        validationExistingScrap(user, item.getId());
        validationIsExistingItem(item.getId());
        Scrap scrap = Scrap.builder()
                .user(user)
                .item(item)
                .build();
        Scrap save = scrapRepository.save(scrap);
        return save;
    }

    @Override
    public void deleteScrap(Long scrapId) {
        validationIsExistingScrap(scrapId);
        Scrap scrap = scrapRepository.findById(scrapId).get();
        scrap.setUser(null);
        scrap.setItem(null);
        scrapRepository.deleteById(scrapId);
    }

//    @Override
//    public void deleteScrap(User user, Item item, Long deleteScrapId) {
//        validationIsExistingScrap(deleteScrapId);
//        Scrap scrap = scrapRepository.findById(deleteScrapId).get();
//        scrap.setUser(null);
//        scrap.setItem(null);
//        scrapRepository.deleteById(deleteScrapId);
//    }

    @Override
    public PaginationDto<List<ScrapDetailsResponse>> getAllScrapsByUserId(Pageable pageable, Long userId) {
        validationExistingScrapByUserId(pageable, userId);
        Page<Scrap> page = scrapRepository.findAllByUserId(Pageable.ofSize(30), userId);
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

    private void validationIsExistingItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isEmpty() == true){
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    private void validationIsExistingScrap(Long deleteScrapId) {
        Optional<Scrap> scrap = scrapRepository.findById(deleteScrapId);
        if(scrap.isEmpty() == true){
            throw new IllegalArgumentException("존재하지 않는 스크랩입니다.");
        }
    }

    private void validationExistingScrap(User user, Long itemId) {
        if (scrapRepository.findAll().isEmpty() != true) {
            List<Scrap> scraps = scrapRepository.findAllByUserId(user.getId());
            for (Scrap scrap : scraps) {
                if(scrap.getItem().getId() == itemId){
                    throw new IllegalArgumentException("이미 스크랩한 상품입니다."); } } }
    }

    private void validationExistingScrapByUserId(Pageable pageable, Long userId) {
        if (scrapRepository.findAllByUserId(pageable, userId).isEmpty() == true) {
            throw new IllegalArgumentException("해당 유저의 스크랩 내역이 존재하지 않습니다.");
        }
    }
}
