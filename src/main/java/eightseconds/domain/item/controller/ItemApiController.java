package eightseconds.domain.item.controller;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.service.FileService;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.dto.GetAllItemsByStatusRequest;
import eightseconds.domain.item.dto.ItemDetailsResponse;
import eightseconds.domain.item.dto.RegisterItemRequest;
import eightseconds.domain.item.dto.RegisterItemResponse;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.item.service.ItemService;
import eightseconds.domain.user.service.UserService;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
                                                       @Valid @RequestPart(value = "registerItemRequest")
                                                               RegisterItemRequest registerItemRequest) throws IOException {
        Item item = itemService.saveItem(SecurityUtil.getCurrentLoginId().get(), registerItemRequest);
        if(files != null ){
            List<MyFile> saveFiles = fileService.save(files);
            itemService.addFiles(item, saveFiles);
        }
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
        itemService.validationUserAndItem(userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get().getItems(), id);
        Item item = itemService.getItem(id);
        return ResponseEntity.ok(ItemDetailsResponse.from(item));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ItemDetailsResponse>> list(@PageableDefault Pageable pageable) { ;
        return ResponseEntity.ok(itemService
                .getItemsByUserId(pageable, userService.getUserWithAuthorities(SecurityUtil.getCurrentLoginId().get()).get().getId())
                .map(ItemDetailsResponse::from));
    }

    @GetMapping("/list/{category}")
    public ResponseEntity<Page<ItemDetailsResponse>> listByCategory(@PageableDefault Pageable pageable, @PathVariable EItemCategory category) { ;
        return ResponseEntity.ok(itemService
                .getItemsByCategory(pageable, category)
                .map(ItemDetailsResponse::from));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> findSoldStatus(@PathVariable Long id) {
        itemService.validationItemId(id);
        itemService.validationSoldStatusByItemId(id);
        Item item = itemService.getItem(id);
        return ResponseEntity.ok(item.getSoldStatus());
    }

    @GetMapping("/list/status")
    public ResponseEntity<?> findListByStatus(@PageableDefault Pageable pageable,
                                              @Valid @RequestBody GetAllItemsByStatusRequest getAllItemsByStatusRequest) {
        Long userId = getAllItemsByStatusRequest.getUserId();
        EItemSoldStatus soldStatus = getAllItemsByStatusRequest.getSoldStatus();
        userService.validationUserId(userId);
        return ResponseEntity.ok(itemService.getItemsByStatusAndUserId(pageable, soldStatus, userId).map(ItemDetailsResponse::from));
    }

}
