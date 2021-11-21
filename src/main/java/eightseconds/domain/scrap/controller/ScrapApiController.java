package eightseconds.domain.scrap.controller;


import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.scrap.dto.*;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.scrap.service.ScrapService;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ScrapRegisterResponse> create(@Valid @RequestBody ScrapRegisterRequest scrapRegisterRequest) throws Exception {

        User user = userService.getUserByUserId(scrapRegisterRequest.getUserId());
        Item item = itemService.getItem(scrapRegisterRequest.getItemId());
        Scrap scrap = scrapService.saveScrap(user, item);
        return ResponseEntity.ok(ScrapRegisterResponse.from("스크랩을 완료했습니다.", scrap.getId()));
    }

    @DeleteMapping("/{scrapId}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Long scrapId) throws Exception {
        scrapService.deleteScrap(scrapId);
        return ResponseEntity.ok(DefaultResponse.from("스크랩 삭제를 완료했습니다."));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<PaginationDto<List<ScrapDetailsResponse>>> list(@PageableDefault Pageable pageable, @PathVariable Long userId) throws Exception {
        userService.validationUserId(userId);
        return ResponseEntity.ok(scrapService.getAllScrapsByUserId(pageable, userId));
    }

    @GetMapping("/heart/{itemId}")
    public ResponseEntity<ScrapCountResponse> getHeart(@PathVariable Long itemId) throws Exception {
        Long heart = scrapService.getAllScrapsByItemIdQuantity(itemId);
        return ResponseEntity.ok(ScrapCountResponse.from(heart));
    }

    @PostMapping("/heart/check")
    public ResponseEntity<ScrapCheckedResponse> checkScrap(@Valid @RequestBody ScrapCheckedRequest scrapCheckedRequest) throws Exception {
        ScrapCheckedResponse checkedScrap = scrapService.isCheckedScrap(scrapCheckedRequest);
        return ResponseEntity.ok(checkedScrap);
    }
}
