package kusitms.domain.file.service;

import kusitms.domain.file.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    MyFile save(MultipartFile files) throws IOException;
}
