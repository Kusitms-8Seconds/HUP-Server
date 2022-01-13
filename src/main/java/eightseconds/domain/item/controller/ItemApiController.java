package eightseconds.domain.item.controller;

import eightseconds.domain.file.service.FileService;
import eightseconds.domain.item.constant.ItemConstants.EItemApiController;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.*;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.dto.SoldOutRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/items")
public class ItemApiController {

    private final ItemService itemService;
    private final FileService fileService;
    private final UserService userService;

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

        return  ResponseEntity.created(location).body(EntityModel.of(registerItemResponse));

    }

    @ApiOperation(value = "아이템 삭제", notes = "등록되어있는 상품을 지웁니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> deleteItem(@PathVariable Long id) {
        itemService.deleteByItemId(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "아이템 조회", notes = "상품을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ItemDetailsResponse>> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(EntityModel.of(itemService.getItem(id)));
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
    public ResponseEntity<EntityModel<PaginationDto<List<ItemDetailsResponse>>>> getAllItemsByCategory(@PageableDefault Pageable pageable, @PathVariable String category) { ;
        return ResponseEntity.ok(EntityModel.of(itemService.getAllItemsByCategory(pageable, category)));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> findSoldStatus(@PathVariable Long id) {
        itemService.validateItemId(id);
        itemService.validateSoldStatusByItemId(id);
        ItemDetailsResponse item = itemService.getItem(id);
        return ResponseEntity.ok(item.getSoldStatus());
    }

    @PostMapping("/list/status")
    public ResponseEntity<PaginationDto<List<ItemDetailsResponse>>> findListByStatus(@PageableDefault Pageable pageable,
                                              @Valid @RequestBody GetAllItemsByStatusRequest getAllItemsByStatusRequest) {
        Long userId = getAllItemsByStatusRequest.getUserId();
        EItemSoldStatus soldStatus = getAllItemsByStatusRequest.getSoldStatus();
        userService.validateUserId(userId);
        return ResponseEntity.ok(itemService.getItemsByStatusAndUserId(pageable, soldStatus, userId));
    }

    // 낙찰 Test용도임 이걸로쓰면 안됨!
    @PostMapping("/soldOutTest")
    public ResponseEntity<ItemDetailsResponse> soldOut(@RequestBody SoldOutRequest soldOutRequest) throws Exception {
        Item item = itemService.soldOutItem(soldOutRequest.getItemId());
        return ResponseEntity.ok(ItemDetailsResponse.from(item));
    }
}
