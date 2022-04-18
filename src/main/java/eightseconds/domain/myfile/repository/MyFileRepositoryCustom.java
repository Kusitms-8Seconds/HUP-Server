package eightseconds.domain.myfile.repository;


import eightseconds.domain.myfile.entity.MyFile;

import java.util.Optional;

public interface MyFileRepositoryCustom {
    Optional<MyFile> findOneNotDeletedByFileId(Long fileId);
}
