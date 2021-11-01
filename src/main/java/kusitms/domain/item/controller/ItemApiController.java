package kusitms.domain.item.controller;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.file.service.FileService;
import kusitms.domain.item.dto.RegisterItemRequest;
import kusitms.domain.item.dto.RegisterItemResponse;
import kusitms.domain.item.entity.Item;
import kusitms.domain.item.service.ItemService;
import kusitms.domain.user.entity.User;
import kusitms.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.xml.transform.Source;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/items")
public class ItemApiController {

    private final ItemService itemService;
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<RegisterItemResponse> registerItem(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                       @Valid @RequestPart(value = "registerItemRequest")
                                                               RegisterItemRequest registerItemRequest) throws IOException {
        List<MyFile> saveFiles = fileService.save(files);
        Item item = itemService.saveItem(SecurityUtil.getCurrentEmail().get(), registerItemRequest);
        itemService.addFiles(item, saveFiles);
        return ResponseEntity.ok(RegisterItemResponse.from(item));
    }
}
