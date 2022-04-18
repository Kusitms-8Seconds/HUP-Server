package eightseconds.domain.myfile.repository;

import eightseconds.domain.myfile.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyFileRepository extends JpaRepository<MyFile, Long>, MyFileRepositoryCustom {

}
