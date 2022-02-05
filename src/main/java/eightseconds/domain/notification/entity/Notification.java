package eightseconds.domain.notification.entity;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.notification.constant.NotificationConstants.ENotificationCategory;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
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
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
    private String targetToken;
    private ENotificationCategory eNotificationCategory;

    public static Notification toEntityFromPriceSuggestion(PriceSuggestion priceSuggestion) {
        String message = createMessageToBidder(priceSuggestion);
        return Notification.builder()
                .user(priceSuggestion.getUser())
                .message(message)
                .targetToken(priceSuggestion.getUser().getTargetToken())
                .eNotificationCategory(ENotificationCategory.eBidder)
                .build();
    }

    public static String createMessageToBidder(PriceSuggestion priceSuggestion){
        return priceSuggestion.getUser().getUsername() + "님 입찰하신 " +
                priceSuggestion.getItem().getItemName() + "물품이 " + priceSuggestion.getSuggestionPrice() +"에 낙찰되었습니다!";
    }

    public static Notification toEntityFromItem(Item item) {
        String message = createMessageToSeller(item);
        return Notification.builder()
                .user(item.getUser())
                .message(message)
                .targetToken(item.getUser().getTargetToken())
                .eNotificationCategory(ENotificationCategory.eSeller)
                .build();
    }

    public static String createMessageToSeller(Item item){
        return item.getUser().getUsername() + "님 경매에 올려놓은 " +
                item.getItemName() + "물품이 " + item.getSoldPrice() +"에 판매되었습니다!";
    }
}
