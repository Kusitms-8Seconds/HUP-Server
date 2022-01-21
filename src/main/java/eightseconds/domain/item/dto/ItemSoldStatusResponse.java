package eightseconds.domain.item.dto;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Builder
public class ItemSoldStatusResponse {

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemConstants.EItemSoldStatus soldStatus;

    public static ItemSoldStatusResponse from(Item item) {

        return ItemSoldStatusResponse.builder()
                .soldStatus(item.getSoldStatus())
                .build();
    }
}
