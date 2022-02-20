package eightseconds.domain.file.service;

import eightseconds.domain.file.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<MyFile> saveMultipleFiles(List<MultipartFile> files) throws IOException;
    MyFile saveSingleFile(MultipartFile files) throws IOException;
    void deleteAllByItemId(Long id);
}
