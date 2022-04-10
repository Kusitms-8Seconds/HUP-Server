package eightseconds.domain.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.item.entity.Item;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category{

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ECategory category;

    @JsonIgnore
    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Item item;

}
