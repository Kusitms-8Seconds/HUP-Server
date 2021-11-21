package eightseconds.domain.item.repository;

import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.soldStatus = :soldStatus")
    Page<Item> findAllByStatus(Pageable pageable, @Param("soldStatus") EItemSoldStatus soldStatus);
    Page<Item> findAllByCategory(Pageable pageable, EItemCategory category);
    Optional<Item> findById(Long itemId);
    @Query("select i from Item i where i.user.id = :userId and i.soldStatus = :soldStatus")
    Page<Item> findAllByStatusAndUserId(Pageable pageable, @Param("soldStatus") EItemSoldStatus soldStatus, @Param("userId") Long userId);

    @Query("select i from Item i where i.soldStatus = :soldStatus")
    List<Item> findAllItemsByStatus(@Param("soldStatus") EItemSoldStatus soldStatus);

}
