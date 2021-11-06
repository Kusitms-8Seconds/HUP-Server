package eightseconds.domain.scrap.entity;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseEntity;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

}
