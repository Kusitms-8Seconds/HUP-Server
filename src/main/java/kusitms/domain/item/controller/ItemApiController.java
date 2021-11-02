package kusitms.domain.item.controller;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.file.service.FileService;
import kusitms.domain.item.dto.DefaultResponse;
import kusitms.domain.item.dto.RegisterItemRequest;
import kusitms.domain.item.dto.RegisterItemResponse;
import kusitms.domain.item.entity.Item;
import kusitms.domain.item.service.ItemService;
import kusitms.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/items")
public class ItemApiController {

    private final ItemService itemService;
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<RegisterItemResponse> registerItem(@Nullable @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       @Valid @RequestPart(value = "registerItemRequest")
                                                               RegisterItemRequest registerItemRequest) throws IOException {
        Item item = itemService.saveItem(SecurityUtil.getCurrentEmail().get(), registerItemRequest);
        if(files != null ){
            List<MyFile> saveFiles = fileService.save(files);
            itemService.addFiles(item, saveFiles);
        }
        return ResponseEntity.ok(RegisterItemResponse.from(item));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponse> deleteItem(@PathVariable Long id) {
        itemService.validationItemId(id);
        fileService.deleteAllByItemId(id);
        itemService.deleteByItemId(id);
        return ResponseEntity.ok(DefaultResponse.from("삭제 완료"));
    }
}
