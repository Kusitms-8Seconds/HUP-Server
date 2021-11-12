package eightseconds.domain.pricesuggestion.entity;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
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
    private boolean acceptState;

    public static PriceSuggestion toEntity(User user, Item item, int suggestionPrice) {
        return PriceSuggestion.builder()
                .user(user)
                .item(item)
                .suggestionPrice(suggestionPrice)
                .acceptState(false)
                .build();
    }

}