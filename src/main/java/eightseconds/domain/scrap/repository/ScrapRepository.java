package eightseconds.domain.scrap.repository;

import eightseconds.domain.scrap.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Page<Scrap> findAllByUserId(Pageable pageable, Long userId);
    List<Scrap> findAllByUserId(Long userId);
    @Query("select count(s) from Scrap s where s.item.id = :itemId")
    Long findItemCountByItemId(@Param("itemId") Long itemId);
    Optional<Scrap> findByUserIdAndItemId(Long userId, Long itemId);

}
