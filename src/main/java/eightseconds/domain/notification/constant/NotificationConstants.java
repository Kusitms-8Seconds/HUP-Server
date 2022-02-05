package eightseconds.domain.notification.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import eightseconds.domain.item.constant.ItemConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationConstants {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ENotificationCategory{
        eBidder("낙찰자"),
        eSeller("판매자");

        private String value;

    }
}
