package kusitms.domain.item.entity;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.item.constant.ItemConstants.EItemCategory;
import kusitms.domain.pricesuggestion.entity.PriceSuggestion;
import kusitms.domain.scrap.entity.Scrap;
import kusitms.domain.user.entity.User;
import kusitms.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private EItemCategory category;
    private int initPrice;
    private int soldPrice;
    private LocalDateTime buyDate;
    private int itemStatePoint;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item")
    private List<PriceSuggestion> priceSuggestions = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<MyFile> myFiles = new ArrayList<>();

//    @Builder
//    public Item(String itemName, Enum category, int initPrice, int soldPrice,
//                                  LocalDateTime buyDate, int itemStatePoint, String description, MyFile... myFiles) {
//        this.itemName = itemName;
//        this.category = category;
//        this.initPrice = initPrice;
//        this.soldPrice = soldPrice;
//        this.buyDate = buyDate;
//        this.itemStatePoint = itemStatePoint;
//        this.description = description;
//        for (MyFile myFile : myFiles) {
//            //this.myFiles.add(myFile);
//            this.addFiles(myFile);
//        }
//    }

    @Builder
    public Item(String itemName, EItemCategory category, int initPrice,
                LocalDateTime buyDate, int itemStatePoint, String description) {
        this.itemName = itemName;
        this.category = category;
        this.initPrice = initPrice;
        this.buyDate = buyDate;
        this.itemStatePoint = itemStatePoint;
        this.description = description;
    }

    // 연관관계 메서드
    public void addFiles(List<MyFile> files){
        for (MyFile file : files) {
            myFiles.add(file);
            file.setItem(this);
        }
    }
}
