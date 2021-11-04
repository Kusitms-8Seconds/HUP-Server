package eightseconds.domain.item.repository;

import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByUserId(Pageable pageable, Long userId);
    Page<Item> findAllByCategory(Pageable pageable, EItemCategory category);

}
