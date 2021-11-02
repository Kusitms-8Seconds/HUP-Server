package kusitms.domain.pricesuggestion.entity;

import kusitms.domain.item.entity.Item;
import kusitms.domain.user.entity.User;
import kusitms.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceSuggestion extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "price_suggestion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int suggestionPrice;
    private boolean soldState;

}