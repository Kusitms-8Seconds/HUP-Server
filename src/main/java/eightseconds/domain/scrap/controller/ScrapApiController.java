package eightseconds.domain.scrap.controller;


import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.scrap.dto.*;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.scrap.service.ScrapService;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/scraps")
public class ScrapApiController {

    private final ScrapService scrapService;
    private final ItemService itemService;
    private final UserService userService;

    @ApiOperation(value = "스크랩 생성", notes = "스크랩을 생성 합니다.")
    @PostMapping
    public ResponseEntity<EntityModel<ScrapRegisterResponse>> createScrap(@Valid @RequestBody ScrapRegisterRequest scrapRegisterRequest){

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(EntityModel.of(scrapService.saveScrap(scrapRegisterRequest)));
    }

    @ApiOperation(value = "스크랩 삭제", notes = "스크랩을 삭제 합니다.")
    @DeleteMapping("/{scrapId}")
    public ResponseEntity<DefaultResponse> deleteScrap(@PathVariable Long scrapId) throws Exception {
        scrapService.deleteScrap(scrapId);
        return ResponseEntity.ok(DefaultResponse.from("스크랩 삭제를 완료했습니다."));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<PaginationDto<List<ScrapDetailsResponse>>> list(@PageableDefault Pageable pageable, @PathVariable Long userId) throws Exception {
        userService.validateUserId(userId);
        return ResponseEntity.ok(scrapService.getAllScrapsByUserId(pageable, userId));
    }

    @GetMapping("/hearts/{itemId}")
    public ResponseEntity<ScrapCountResponse> getHeart(@PathVariable Long itemId) throws Exception {
        Long heart = scrapService.getAllScrapsByItemIdQuantity(itemId);
        return ResponseEntity.ok(ScrapCountResponse.from(heart));
    }

    @PostMapping("/hearts")
    public ResponseEntity<ScrapCheckedResponse> checkScrap(@Valid @RequestBody ScrapCheckedRequest scrapCheckedRequest) throws Exception {
        ScrapCheckedResponse checkedScrap = scrapService.isCheckedScrap(scrapCheckedRequest);
        return ResponseEntity.ok(checkedScrap);
    }
}
