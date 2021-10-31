package kusitms.domain.file.repository;

import kusitms.domain.file.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<MyFile, Long> {
}
