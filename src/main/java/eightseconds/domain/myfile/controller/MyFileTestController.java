package eightseconds.domain.myfile.controller;

import eightseconds.domain.myfile.entity.MyFile;
import eightseconds.domain.myfile.service.MyFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/myfiles")
@Api(tags = "MyFile TEST API")
public class MyFileTestController {
    private final MyFileService myFileService;

    @ApiOperation(value = "파일 저장 TEST", notes = "파일 저장 TEST")
    @PostMapping
    public ResponseEntity<String> createFile(@RequestPart(value = "file", required = false) MultipartFile multipartFile){
        MyFile myFile = myFileService.saveImage(multipartFile);
        return ResponseEntity.ok(myFile.getFileKey());
    }


    @ApiOperation(value = "파일 여러장 저장 TEST", notes = "파일 여러장 저장 TEST")
    @PostMapping("/multi")
    public ResponseEntity<String> createFiles(@RequestPart(value = "files", required = false) List<MultipartFile> multipartFiles){
        List<MyFile> myFiles = myFileService.saveImages(multipartFiles);
        return ResponseEntity.ok(String.valueOf(myFiles.size()));
    }

    @ApiOperation(value = "파일 가져오기 TEST", notes = "파일 가져오기 TEST")
    @GetMapping("/{fileId}")
    public ResponseEntity<String> getFile(@PathVariable Long fileId){
        MyFile file = myFileService.getFile(fileId);
        return ResponseEntity.ok(file.getFileKey());
    }

    @ApiOperation(value = "파일 삭제 TEST", notes = "파일 삭제 TEST")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId){
        boolean b = myFileService.deleteFile(fileId);
        return ResponseEntity.ok(String.valueOf(b));
    }

}
