package eightseconds.domain.item.controller;

import eightseconds.domain.item.constant.ItemConstants.EItemApiController;
import eightseconds.domain.item.dto.*;
import eightseconds.domain.item.service.ItemService;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.dto.PaginationDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/items")
public class ItemApiController {

    private final ItemService itemService;

    @ApiOperation(value = "아이템 생성", notes = "상품등록을 합니다.")
    @PostMapping
    public ResponseEntity<EntityModel<RegisterItemResponse>> createItem(@Nullable @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       @RequestPart(value = "userId") String userId,
                                                       @RequestPart(value = "itemName", required = false) String itemName,
                                                       @RequestPart(value = "category", required = false) String category,
                                                       @RequestPart(value = "initPrice", required = false) String initPrice,
                                                       @RequestPart(value = "buyDate", required = false) String buyDate,
                                                       @RequestPart(value = "itemStatePoint", required = false) String itemStatePoint,
                                                       @RequestPart(value = "description", required = false) String description,
                                                       @RequestPart(value = "auctionClosingDate", required = false) String auctionClosingDate) throws IOException {

        RegisterItemResponse registerItemResponse = itemService.saveItem(Long.valueOf(userId), RegisterItemRequest.of(itemName, category, initPrice
                , buyDate, itemStatePoint, description, auctionClosingDate, files));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(EItemApiController.eLocationIdPath.getValue())
                .buildAndExpand(registerItemResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(EntityModel.of(registerItemResponse)
                .add(linkTo(methodOn(this.getClass()).createItem(files, userId, itemName, category, initPrice,
                        buyDate, itemStatePoint, description, auctionClosingDate)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).getItem(registerItemResponse.getId())).withRel(EItemApiController.eGetMethod.getValue()))
                .add(linkTo(methodOn(this.getClass()).deleteItem(registerItemResponse.getId())).withRel(EItemApiController.eDeleteMethod.getValue())));

    }

    @ApiOperation(value = "아이템 조회", notes = "상품을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ItemDetailsResponse>> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(EntityModel.of(itemService.getItem(id))
                .add(linkTo(methodOn(this.getClass()).getItem(id)).withSelfRel())
                .add(linkTo(methodOn(this.getClass()).deleteItem(id)).withRel(EItemApiController.eDeleteMethod.getValue())));
    }

    @ApiOperation(value = "아이템 삭제", notes = "등록되어있는 상품을 지웁니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteItem(@PathVariable Long id) {
        itemService.deleteByItemId(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "아이템 판매상태별 조회", notes = "상품을 판매상태별로 조회합니다.")
    @GetMapping("/statuses/{itemSoldStatus}")
    public ResponseEntity<EntityModel<PaginationDto<List<ItemDetailsResponse>>>> getAllItemsByItemSoldStatus(@PageableDefault Pageable pageable, @PathVariable String itemSoldStatus) {
        return ResponseEntity.ok(EntityModel.of(itemService.getAllItemsByItemSoldStatus(pageable, itemSoldStatus)));
    }

    @ApiOperation(value = "아이템 판매상태별, 스크랩많은순 조회", notes = "상품을 판매상태별, 스크랩이 많은순으로 조회합니다.")
    @GetMapping("/statuses/hearts/{itemSoldStatus}")
    public ResponseEntity<List<BestItemResponse>> getAllItemsByItemSoldStatusAndHeart(@PathVariable String itemSoldStatus) {
        return ResponseEntity.ok(itemService.getAllBestItemsByItemSoldStatus(itemSoldStatus));
    }

    @ApiOperation(value = "아이템 카테고리별 조회", notes = "상품을 카테고리별로 조회합니다.")
    @GetMapping("/categories/{category}")
    public ResponseEntity<EntityModel<PaginationDto<List<ItemDetailsResponse>>>> getAllItemsByCategory(@PageableDefault Pageable pageable, @PathVariable String category) {
        return ResponseEntity.ok(EntityModel.of(itemService.getAllItemsByCategory(pageable, category)));
    }

    @ApiOperation(value = "유저가 등록한 상품을 판매 상태별로 조회", notes = "해당 유저가 등록한 상품을 판매 상태별로 조회합니다.")
    @PostMapping("/users")
    public ResponseEntity<EntityModel<PaginationDto<List<ItemDetailsResponse>>>> getAllItemsOfUser(@PageableDefault Pageable pageable,
                                              @Valid @RequestBody ItemOfUserRequest itemOfUserRequest) {
        return ResponseEntity.ok(EntityModel.of(itemService.getAllItemsOfUser(pageable, itemOfUserRequest)));
    }

    @ApiOperation(value = "상품을 낙찰합니다.", notes = "해당 유저가 등록한 상품을 낙찰합니다.")
    @PostMapping("/sold")
    public ResponseEntity<EntityModel<SoldResponse>> soldItem(@RequestBody SoldRequest soldRequest){
        return ResponseEntity.ok(EntityModel.of(itemService.soldOutItem(soldRequest)));
    }
}
