package eightseconds.domain.item.dto;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterItemRequest {

    @NotNull private String itemName;
    @NotNull private EItemCategory category;
    @NotNull private int initPrice;
    @NotNull private LocalDateTime buyDate;
    @NotNull private int itemStatePoint;
    private String description;
    @NotNull private LocalDateTime auctionClosingDate;

    public static RegisterItemRequest of(String itemName, String category, String initPrice, String buyDate, String itemStatePoint
            , String description, String auctionClosingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime buyDateFormatted = LocalDateTime.parse(buyDate, formatter);
        LocalDateTime auctionClosingDateFormatted = LocalDateTime.parse(auctionClosingDate, formatter);

        System.out.println("밸류오브카테고리"+EItemCategory.valueOf(category));

        return RegisterItemRequest.builder()
                .itemName(itemName)
                .category(EItemCategory.valueOf(category))
                .initPrice(Integer.valueOf(initPrice))
                .buyDate(buyDateFormatted)
                .itemStatePoint(Integer.valueOf(itemStatePoint))
                .description(description)
                .auctionClosingDate(auctionClosingDateFormatted)
                .build();
    }

    public Item toEntity(){
        return Item.builder()
                .itemName(itemName)
                .category(EItemCategory.from(category.toString()))
                .initPrice(initPrice)
                .buyDate(buyDate)
                .itemStatePoint(itemStatePoint)
                .description(description)
                .auctionClosingDate(auctionClosingDate)
                .build();
    }
}
