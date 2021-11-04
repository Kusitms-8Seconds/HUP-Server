package eightseconds.domain.scrap.repository;

import eightseconds.domain.scrap.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Page<Scrap> findAllByUserId(Pageable pageable, Long userId);
}
