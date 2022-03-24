package eightseconds.domain.file.service;

import eightseconds.domain.file.constant.FileConstants;
import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.file.exception.FileNotFoundException;
import eightseconds.domain.file.exception.FileToSaveNotExistException;
import eightseconds.domain.file.repository.FileRepository;
import eightseconds.domain.file.constant.FileConstants.EFileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
                String path = System.getProperty(EFileServiceImpl.eBaseDir.getValue()) +
                        EFileServiceImpl.eImagesDir.getValue();
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
            String path = System.getProperty(EFileServiceImpl.eBaseDir.getValue()) +
                    EFileServiceImpl.eImagesDir.getValue();
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

    @Override
    public byte[] getFile(String name) {
        try (InputStream imageStream = new FileInputStream(getFileURL(name))){
            return IOUtils.toByteArray(imageStream);
        }
        catch (IOException e) {throw new FileNotFoundException(FileConstants.EFileApiController.eFileNotFoundExceptionMessage.getValue());}
    }

    public String getFileURL(String name) {
        return System.getProperty(FileConstants.EFileApiController.eBaseDir.getValue()) +
                FileConstants.EFileApiController.eImagesDir.getValue() + name;
    }

    /**
     * validate
     */

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) throw new FileToSaveNotExistException(EFileServiceImpl.eFileToSaveNotExistExceptionMessage.getValue());
    }

}
