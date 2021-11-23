package eightseconds.domain.scrap.dto;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.scrap.entity.Scrap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ScrapDetailsResponse {

    @NotNull private Long id;
    @NotNull private Long userId;
    @NotNull private Long itemId;
    @NotNull private String itemName;
    @NotNull @Enumerated(EnumType.STRING) private ItemConstants.EItemCategory category;
    @NotNull private int initPrice;
    private int soldPrice;
    @NotNull private LocalDateTime buyDate;
    @NotNull private int itemStatePoint;
    private String description;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;

    public static ScrapDetailsResponse from(Scrap scrap) {
        List<String> fileNames = new ArrayList<>();
        if (scrap.getItem().getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = scrap.getItem().getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        return ScrapDetailsResponse.builder()
                .id(scrap.getId())
                .userId(scrap.getUser().getId())
                .itemId(scrap.getItem().getId())
                .itemName(scrap.getItem().getItemName())
                .category(scrap.getItem().getCategory())
                .initPrice(scrap.getItem().getInitPrice())
                .soldPrice(scrap.getItem().getSoldPrice())
                .buyDate(scrap.getItem().getBuyDate())
                .itemStatePoint(scrap.getItem().getItemStatePoint())
                .description(scrap.getItem().getDescription())
                .fileNames(fileNames)
                .auctionClosingDate(scrap.getItem().getAuctionClosingDate())
                .build();
    }
}
