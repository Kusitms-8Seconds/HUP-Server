package eightseconds.domain.pricesuggestion.entity;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseEntity;
import eightseconds.global.entity.BaseTimeEntity;
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
public class PriceSuggestion extends BaseTimeEntity {

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