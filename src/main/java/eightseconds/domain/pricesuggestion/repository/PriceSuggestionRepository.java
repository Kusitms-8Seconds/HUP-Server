package eightseconds.domain.pricesuggestion.repository;

import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceSuggestionRepository extends JpaRepository<PriceSuggestion, Long> {
    Page<PriceSuggestion> findAllByItemId(Pageable pageable, Long itemId);
    List<PriceSuggestion> findAllByItemId(Long itemId);
    Optional<PriceSuggestion> getByUserIdAndItemId(Long userId, Long itemId);

    @Query("SELECT p FROM PriceSuggestion p WHERE p.item.id = :itemId and p.suggestionPrice = (SELECT max(subP.suggestionPrice) FROM PriceSuggestion subP)")
    Optional<PriceSuggestion> getMaximumPriceByItemId(@Param("itemId") Long itemId);

    Optional<PriceSuggestion> getByItemId(Long itemId);

    @Query("select count(p) from PriceSuggestion p where p.item.id = :itemId")
    int findPriceSuggestionCountByItemId(@Param("itemId") Long itemId);

    Page<PriceSuggestion> findAllByUserId(Pageable pageable, Long userId);
}
