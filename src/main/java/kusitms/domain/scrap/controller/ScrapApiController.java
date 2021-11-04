package kusitms.domain.scrap.controller;


import kusitms.domain.item.entity.Item;
import kusitms.domain.item.service.ItemService;
import kusitms.domain.scrap.dto.ScrapDetailsResponse;
import kusitms.domain.scrap.entity.Scrap;
import kusitms.domain.scrap.service.ScrapService;
import kusitms.domain.user.entity.User;
import kusitms.domain.user.service.UserService;
import kusitms.global.dto.DefaultResponse;
import kusitms.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping("{id}")
    public ResponseEntity<?> create(@PathVariable Long id) throws Exception {

        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Item item = itemService.getItem(id);
        scrapService.saveScrap(user, item);
        return ResponseEntity.ok(DefaultResponse.from("스크랩을 완료했습니다."));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {

        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Item item = itemService.getItem(id);
        scrapService.deleteScrap(user, item, id);
        return ResponseEntity.ok(DefaultResponse.from("스크랩 삭제를 완료했습니다."));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@PageableDefault Pageable pageable) throws Exception {

        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Page<Scrap> allScraps = scrapService.getAllScrapsByUserId(pageable, user.getId());
        return ResponseEntity.ok(allScraps.map(ScrapDetailsResponse::from));
    }

}
