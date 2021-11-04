package kusitms.domain.file.controller;

import kusitms.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileApiController {

    private final FileService fileService;

    @PostMapping("file-save")
    public ResponseEntity<?> save(@RequestPart List<MultipartFile> files) throws Exception {
        fileService.save(files);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> find(@PathVariable String name){
        try (InputStream imageStream = new FileInputStream(System.getProperty("user.dir") + "/images/" + name)) {
            return ResponseEntity.ok(IOUtils.toByteArray(imageStream));
        } catch (IOException e) {
            throw new IllegalArgumentException("해당 파일을 찾을 수 업습니다.");
        }
    }
}
