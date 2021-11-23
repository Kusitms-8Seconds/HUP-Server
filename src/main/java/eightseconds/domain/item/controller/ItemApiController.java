package eightseconds.domain.item.controller;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.service.FileService;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.*;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.pricesuggestion.dto.SoldOutRequest;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/items")
public class ItemApiController {

    private final ItemService itemService;
    private final FileService fileService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterItemResponse> create(@Nullable @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       @RequestPart(value = "userId", required = true) String userId,
                                                       @RequestPart(value = "itemName", required = false) String itemName,
                                                       @RequestPart(value = "category", required = false) String category,
                                                       @RequestPart(value = "initPrice", required = false) String initPrice,
                                                       @RequestPart(value = "buyDate", required = false) String buyDate,
                                                       @RequestPart(value = "itemStatePoint", required = false) String itemStatePoint,
                                                       @RequestPart(value = "description", required = false) String description,
                                                       @RequestPart(value = "auctionClosingDate", required = false) String auctionClosingDate) throws IOException {

        Item item = itemService.saveItem(Long.valueOf(userId), RegisterItemRequest.of(itemName, category, initPrice
                , buyDate, itemStatePoint, description, auctionClosingDate));
        System.out.println("files널이아닌지?"+files);
        if(files != null ){
            System.out.println("files널이아닌지22?"+files);
            List<MyFile> saveFiles = fileService.save(files);
            itemService.addFiles(item, saveFiles); }
        return ResponseEntity.ok(RegisterItemResponse.from(item));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable Long id) {
        itemService.validationItemId(id);
        fileService.deleteAllByItemId(id);
        itemService.deleteByItemId(id);
        return ResponseEntity.ok(DefaultResponse.from("삭제를 완료했습니다."));
    }

    @GetMapping("{id}")
    public ResponseEntity<ItemDetailsResponse> find(@PathVariable Long id) { ;
        itemService.validationItemId(id);
        Item item = itemService.getItem(id);
        return ResponseEntity.ok(ItemDetailsResponse.from(item));
    }

    @GetMapping("/list/status/{itemSoldStatus}")
    public ResponseEntity<PaginationDto<List<ItemDetailsResponse>>> list(@PageableDefault Pageable pageable, @PathVariable EItemSoldStatus itemSoldStatus) {
        return ResponseEntity.ok(itemService.getAllItems(pageable, itemSoldStatus));
    }

    @GetMapping("/list/status/heart/{itemSoldStatus}")
    public ResponseEntity<List<BestItemResponse>> listBestItems(@PathVariable EItemSoldStatus itemSoldStatus) {
        return ResponseEntity.ok(itemService.getAllBestItems(itemSoldStatus));
    }

    @GetMapping("/list/category/{category}")
    public ResponseEntity<PaginationDto<List<ItemDetailsResponse>>> listByCategory(@PageableDefault Pageable pageable, @PathVariable EItemCategory category) { ;
        return ResponseEntity.ok(itemService.getItemsByCategory(pageable, category));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> findSoldStatus(@PathVariable Long id) {
        itemService.validationItemId(id);
        itemService.validationSoldStatusByItemId(id);
        Item item = itemService.getItem(id);
        return ResponseEntity.ok(item.getSoldStatus());
    }

    @PostMapping("/list/status")
    public ResponseEntity<PaginationDto<List<ItemDetailsResponse>>> findListByStatus(@PageableDefault Pageable pageable,
                                              @Valid @RequestBody GetAllItemsByStatusRequest getAllItemsByStatusRequest) {
        Long userId = getAllItemsByStatusRequest.getUserId();
        EItemSoldStatus soldStatus = getAllItemsByStatusRequest.getSoldStatus();
        userService.validationUserId(userId);
        return ResponseEntity.ok(itemService.getItemsByStatusAndUserId(pageable, soldStatus, userId));
    }

    // 낙찰 Test용도임 이걸로쓰면 안됨!
    @PostMapping("/soldOutTest")
    public ResponseEntity<ItemDetailsResponse> soldOut(@RequestBody SoldOutRequest soldOutRequest) throws Exception {
        Item item = itemService.soldOutItem(soldOutRequest.getItemId());
        return ResponseEntity.ok(ItemDetailsResponse.from(item));
    }
}
