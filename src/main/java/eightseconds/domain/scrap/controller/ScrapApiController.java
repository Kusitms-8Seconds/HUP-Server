package eightseconds.domain.scrap.controller;


import eightseconds.domain.scrap.constant.ScrapConstants;
import eightseconds.domain.scrap.dto.*;
import eightseconds.domain.scrap.service.ScrapService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/scraps")
public class ScrapApiController {

    private final ScrapService scrapService;

    @ApiOperation(value = "스크랩 생성", notes = "스크랩을 생성 합니다.")
    @PostMapping
    public ResponseEntity<EntityModel<ScrapRegisterResponse>> createScrap(@Valid @RequestBody ScrapRegisterRequest scrapRegisterRequest){

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(location).body(EntityModel.of(scrapService.saveScrap(scrapRegisterRequest))
                .add(linkTo(methodOn(this.getClass()).createScrap(scrapRegisterRequest)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).deleteScrap(null)).withRel(ScrapConstants.EScrapApiController.eDeleteMethod.getValue())));
    }

    @ApiOperation(value = "스크랩 삭제", notes = "스크랩을 삭제 합니다.")
    @DeleteMapping("/{scrapId}")
    public ResponseEntity<DefaultResponse> deleteScrap(@PathVariable Long scrapId){
        scrapService.deleteScrap(scrapId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "유저의 스크랩 내역 조회", notes = "유저의 스크랩 내역을 조회합니다.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<EntityModel<PaginationDto<List<ScrapDetailsResponse>>>> getAllScrapsOfUser(@PageableDefault Pageable pageable, @PathVariable Long userId){
        return ResponseEntity.ok(EntityModel.of(scrapService.getAllScrapsByUserId(pageable, userId)));
    }

    @ApiOperation(value = "상품의 좋아요 수 조회", notes = "상품의 좋아요 수를 조회합니다.")
    @GetMapping("/hearts/{itemId}")
    public ResponseEntity<EntityModel<ScrapCountResponse>> getHeart(@PathVariable Long itemId){
        return ResponseEntity.ok(EntityModel.of(scrapService.getAllScraps(itemId)));
    }

    @ApiOperation(value = "유저가 해당 상품을 스크랩중인지 조회", notes = "유저가 해당 상품을 스크랩중인지 조회합니다.")
    @GetMapping("/hearts")
    public ResponseEntity<EntityModel<ScrapCheckedResponse>> checkScrap(@Valid @RequestBody ScrapCheckedRequest scrapCheckedRequest){
        return ResponseEntity.ok(EntityModel.of(scrapService.isCheckedScrap(scrapCheckedRequest)));
    }
}
