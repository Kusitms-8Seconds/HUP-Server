package eightseconds.domain.file.repository;

import eightseconds.domain.file.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<MyFile, Long>{

    void deleteAllByItemId(Long id);
}
