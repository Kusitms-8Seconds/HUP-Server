package eightseconds.domain.file.controller;

import eightseconds.domain.file.constant.FileConstants;
import eightseconds.domain.file.constant.FileConstants.EFileApiController;
import eightseconds.domain.file.exception.FileNotFoundException;
import eightseconds.domain.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
@RequestMapping("api/v1/files")
@Api(tags = "File API")
public class FileApiController {

    private final FileService fileService;
    @Value("${resources.location}")
    private String path;

    @ApiOperation(value = "이미지 파일 생성", notes = "이미지 파일 등록을 합니다.")
    @PostMapping
    public ResponseEntity<?> createFile(@RequestPart List<MultipartFile> files) throws Exception {
        fileService.saveMultipleFiles(files);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "이미지 파일 가져오기", notes = "이미지 파일을 조회 합니다.")
    @GetMapping(value = "{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getFile(@PathVariable String name) {
//        try (InputStream imageStream = new FileInputStream(System.getProperty(FileConstants.EFileApiController.eBaseDir.getValue()) +
//                FileConstants.EFileApiController.eImagesDir.getValue() + name))

        try (InputStream imageStream = new FileInputStream(path + name))
        {
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(imageStream), HttpStatus.OK);
        } catch (IOException e) {
            throw new FileNotFoundException(EFileApiController.eFileNotFoundExceptionMessage.getValue());
        }
    }
}
