package kusitms.domain.file.repository;

import kusitms.domain.file.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<MyFile, Long>{

    void deleteAllByItemId(Long id);
}
