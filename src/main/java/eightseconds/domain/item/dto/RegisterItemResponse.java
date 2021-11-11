package eightseconds.domain.item.dto;

import eightseconds.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RegisterItemResponse {

    private Long id;

    private final String itemName;
    private Enum category;
    private int initPrice;
    private LocalDateTime buyDate;
    private int itemStatePoint;
    private String description;
    private Enum soldStatus;

    public static RegisterItemResponse from(Item item) {
        return RegisterItemResponse.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .category(item.getCategory())
                .initPrice(item.getInitPrice())
                .buyDate(item.getBuyDate())
                .itemStatePoint(item.getItemStatePoint())
                .description(item.getDescription())
                .soldStatus(item.getSoldStatus())
                .build();
    }
}
