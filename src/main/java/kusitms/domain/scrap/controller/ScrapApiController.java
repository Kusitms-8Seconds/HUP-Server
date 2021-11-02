package kusitms.domain.scrap.controller;


import kusitms.domain.item.entity.Item;
import kusitms.domain.item.service.ItemService;
import kusitms.domain.scrap.service.ScrapService;
import kusitms.domain.user.entity.User;
import kusitms.domain.user.service.UserService;
import kusitms.global.dto.DefaultResponse;
import kusitms.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/scrap")
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping("{id}")
    public ResponseEntity<?> scrapItem(@PathVariable Long id) throws Exception {

        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Item item = itemService.getItem(id);
        scrapService.saveScrap(user, item);
        return ResponseEntity.ok(DefaultResponse.from("스크랩을 완료했습니다."));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteScrap(@PathVariable Long id) throws Exception {

        User user = userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get();
        Item item = itemService.getItem(id);
        scrapService.deleteScrap(user, item, id);
        return ResponseEntity.ok(DefaultResponse.from("스크랩 삭제를 완료했습니다."));
    }
}
