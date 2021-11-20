package eightseconds.domain.scrap.controller;


import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.scrap.dto.ScrapCountResponse;
import eightseconds.domain.scrap.dto.ScrapDetailsResponse;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.scrap.service.ScrapService;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.dto.PaginationDto;
import eightseconds.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping("{id}")
    public ResponseEntity<DefaultResponse> create(@PathVariable Long id) throws Exception {
        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Item item = itemService.getItem(id);
        scrapService.saveScrap(user, item);
        return ResponseEntity.ok(DefaultResponse.from("스크랩을 완료했습니다."));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Long id) throws Exception {
        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Item item = itemService.getItem(id);
        scrapService.deleteScrap(user, item, id);
        return ResponseEntity.ok(DefaultResponse.from("스크랩 삭제를 완료했습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<PaginationDto<List<ScrapDetailsResponse>>> list(@PageableDefault Pageable pageable) throws Exception {
        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        return ResponseEntity.ok(scrapService.getAllScrapsByUserId(pageable, user.getId()));
    }

    @GetMapping("/heart/{id}")
    public ResponseEntity<ScrapCountResponse> getHeart(@PathVariable Long id) throws Exception {
        Long heart = scrapService.getAllScrapsByItemIdQuantity(id);
        return ResponseEntity.ok(ScrapCountResponse.from(heart));
    }

}
