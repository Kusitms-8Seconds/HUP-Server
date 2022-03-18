package eightseconds.domain.file.service;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.exception.FileToSaveNotExistException;
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

    @Transactional
    public List<MyFile> saveMultipleFiles(List<MultipartFile> files) throws IOException {
        List<MyFile> savedFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                validateFile(file);
                String originFilename = file.getOriginalFilename();
                String extension = FilenameUtils.getExtension(Objects.requireNonNull(originFilename)).toLowerCase();
//                String path = System.getProperty(EFileServiceImpl.eBaseDir.getValue()) +
//                        EFileServiceImpl.eImagesDir.getValue();
                String path = System.getProperty("user.home") + "hup/images/";
                File saveFile;
                String fileName;
                do {
                    fileName = UUID.randomUUID() + EFileServiceImpl.eDot.getValue() + extension;
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

    @Transactional
    public MyFile saveSingleFile(MultipartFile file) throws IOException {
            validateFile(file);
            String originFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(Objects.requireNonNull(originFilename)).toLowerCase();
//            String path = System.getProperty(EFileServiceImpl.eBaseDir.getValue()) +
//                    EFileServiceImpl.eImagesDir.getValue();
            String path = System.getProperty("user.home") + "hup/images/";
            File saveFile;
            String fileName;
            do {
                fileName = UUID.randomUUID() + EFileServiceImpl.eDot.getValue() + extension;
                saveFile = new File(path + fileName);
            } while (saveFile.exists());
            saveFile.mkdirs();
            file.transferTo(saveFile);
            MyFile savedFile = fileRepository.save(MyFile.builder()
                    .filename(fileName)
                    .fileOriginName(originFilename)
                    .fileUrl(path)
                    .build());
        return savedFile;
    }

    @Override
    @Transactional
    public void deleteAllByItemId(Long id) {
        fileRepository.deleteAllByItemId(id);
    }

    /**
     * validate
     */

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) throw new FileToSaveNotExistException(EFileServiceImpl.eFileToSaveNotExistExceptionMessage.getValue());
    }

}
