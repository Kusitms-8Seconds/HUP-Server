package eightseconds.domain.file.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.repository.FileRepository;
import eightseconds.domain.file.constant.FileConstants.EFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public List<MyFile> save(List<MultipartFile> files) throws IOException {
        //if (files.isEmpty()) throw new FileToSaveNotExistException(EFileServiceImpl.FILE_TO_SAVE_NOT_EXIST_EXCEPTION_MESSAGE.getMessage());
        List<MyFile> savedFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                String originFilename = file.getOriginalFilename();
                String extension = FilenameUtils.getExtension(Objects.requireNonNull(originFilename)).toLowerCase();
                String path = System.getProperty(EFileServiceImpl.BASE_DIR.getMessage()) + "/" + EFileServiceImpl.IMAGES_DIR.getMessage();
                File saveFile;
                String fileName;
                do {
                    fileName = UUID.randomUUID() + "." + extension;
                    saveFile = new File(path + fileName);
                } while (saveFile.exists());
                saveFile.mkdirs();
                file.transferTo(saveFile);
                MyFile savedFile = fileRepository.save(MyFile.builder()
                        .filename(fileName)
                        .fileOriginName(originFilename)
                        .fileUrl(path)
                        .build());
                savedFiles.add(savedFile);
            }
            return savedFiles;
    }

    @Override
    @Transactional
    public void deleteAllByItemId(Long id) {
        fileRepository.deleteAllByItemId(id);
    }


}
