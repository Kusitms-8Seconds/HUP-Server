package eightseconds.domain.item.entity;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItem;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.exception.NotBidItemException;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
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
public class Item extends BaseTimeEntity {

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
    @Enumerated(EnumType.STRING)
    private EItemSoldStatus soldStatus;
    private LocalDateTime auctionClosingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<PriceSuggestion> priceSuggestions = new ArrayList<>();

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<MyFile> myFiles = new ArrayList<>();

//    @OneToMany(mappedBy = "item")
//    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Builder
    public Item(String itemName, EItemCategory category, int initPrice,
                LocalDateTime buyDate, int itemStatePoint, String description, LocalDateTime auctionClosingDate) {
        this.itemName = itemName;
        this.category = category;
        this.initPrice = initPrice;
        this.buyDate = buyDate;
        this.itemStatePoint = itemStatePoint;
        this.description = description;
        this.soldStatus = EItemSoldStatus.eOnGoing;
        this.auctionClosingDate = auctionClosingDate;
    }

    // 연관관계 메서드
    public void addFiles(List<MyFile> files){
        for (MyFile file : files) {
            System.out.println("myfilesiaze"+myFiles.size());
            myFiles.add(file);
            file.setItem(this);
        }
    }
    public void setUser(User user) {
        this.user = user;
        user.getItems().add(this);
    }

    // 비즈니스 로직
    public PriceSuggestion getMaximumPriceEntity() {
        int maximumPrice = EItem.eZero.getNumber();
        PriceSuggestion priceSuggestionByMaximumPrice = null;
        for (PriceSuggestion priceSuggestion : this.getPriceSuggestions()) {
            if(maximumPrice < priceSuggestion.getSuggestionPrice()){
                maximumPrice = priceSuggestion.getSuggestionPrice();
                priceSuggestionByMaximumPrice = priceSuggestion;
            }
        }
        if (maximumPrice == EItem.eZero.getNumber()) throw new NotBidItemException(EItem.eNotBidItemExceptionMessage.getValue());
        return priceSuggestionByMaximumPrice;
    }
}
