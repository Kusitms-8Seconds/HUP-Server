package eightseconds.domain.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.chatroom.entity.ChatRoom;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ECategory category;

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<UserCategory> userCategories = new ArrayList<>();
}
