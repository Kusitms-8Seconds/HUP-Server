package eightseconds.domain.myfile.service;

import eightseconds.domain.myfile.entity.MyFile;
import eightseconds.domain.myfile.exception.NotFoundMyFileException;
import eightseconds.domain.myfile.repository.MyFileRepository;
import eightseconds.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyFileServiceImpl implements MyFileService {

    private final FileService fileService;
    private final MyFileRepository myFileRepository;

    @Override
    public List<MyFile> saveImages(List<MultipartFile> multipartFiles) {
        List<MyFile> myFiles = new ArrayList<>();
        if(multipartFiles!=null)
            for (MultipartFile multipartFile : multipartFiles)
                myFiles.add(this.myFileRepository.save(MyFile.toEntity(this.fileService.saveImage(multipartFile))));
        return myFiles;
    }

    @Override
    public MyFile saveImage(MultipartFile multipartFile) {
        return this.myFileRepository.save(MyFile.toEntity(this.fileService.saveImage(multipartFile)));
    }

    @Override
    public MyFile getFile(Long fileId) {
        return this.myFileRepository.findOneNotDeletedByFileId(fileId).orElseThrow(NotFoundMyFileException::new);
    }

    @Override
    @Transactional
    public boolean deleteFile(Long fileId) {
        this.findByFileId(fileId).setDeleted(true);
        return true;
    }

    private MyFile findByFileId(Long fileId) {
        return this.myFileRepository.findById(fileId).orElseThrow(NotFoundMyFileException::new);
    }

}
