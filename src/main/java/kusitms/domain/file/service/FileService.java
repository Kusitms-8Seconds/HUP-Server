package kusitms.domain.file.service;

import kusitms.domain.file.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<MyFile> save(List<MultipartFile> files) throws IOException;
    void deleteAllByItemId(Long id);
}
