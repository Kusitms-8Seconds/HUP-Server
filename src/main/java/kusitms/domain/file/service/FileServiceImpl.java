package kusitms.domain.file.service;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.file.exception.FileToSaveNotExistException;
import kusitms.domain.file.repository.FileRepository;
import kusitms.domain.file.constant.FileConstants.EFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public MyFile save(MultipartFile files) throws IOException {
        if (files.isEmpty()) throw new FileToSaveNotExistException(EFileServiceImpl.FILE_TO_SAVE_NOT_EXIST_EXCEPTION_MESSAGE.getMessage());
        String originFilename = files.getOriginalFilename();
        String extension = FilenameUtils.getExtension(Objects.requireNonNull(originFilename)).toLowerCase();
        String path = System.getProperty(EFileServiceImpl.BASE_DIR.getMessage()) + "/" + EFileServiceImpl.IMAGES_DIR.getMessage();
        File saveFile;
        String fileName;
        do {
            fileName = UUID.randomUUID() + "." + extension;
            saveFile = new File(path + fileName);
        } while (saveFile.exists());
        saveFile.mkdirs();
        files.transferTo(saveFile);
        return fileRepository.save(MyFile.builder()
                .filename(fileName)
                .fileOriginName(originFilename)
                .fileUrl(path)
                .build());
    }
}
