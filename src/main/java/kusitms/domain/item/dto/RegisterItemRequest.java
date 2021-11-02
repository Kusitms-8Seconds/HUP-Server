package kusitms.domain.item.dto;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.item.constant.ItemConstants.EItemCategory;
import kusitms.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterItemRequest {

    @NotNull private String itemName;
    @NotNull private String category;
    @NotNull private int initPrice;
    @NotNull private LocalDateTime buyDate;
    @NotNull private int itemStatePoint;
    private String description;

    public Item toEntity(){
        return Item.builder()
                .itemName(itemName)
                .category(EItemCategory.from(category))
                .initPrice(initPrice)
                .buyDate(buyDate)
                .itemStatePoint(itemStatePoint)
                .description(description)
                .build();
    }
}
