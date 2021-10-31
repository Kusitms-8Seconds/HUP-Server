package kusitms.domain.item.entity;

import kusitms.domain.file.entity.MyFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    @OneToMany(mappedBy = "item")
    private List<MyFile> myFile = new ArrayList<>();

}
