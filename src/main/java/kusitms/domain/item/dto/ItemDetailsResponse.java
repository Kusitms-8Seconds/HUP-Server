package kusitms.domain.item.dto;

import kusitms.domain.file.entity.MyFile;
import kusitms.domain.item.constant.ItemConstants.EItemCategory;
import kusitms.domain.item.entity.Item;
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
public class ItemDetailsResponse {

    @NotNull private Long id;
    @NotNull private String itemName;
    @NotNull @Enumerated(EnumType.STRING) private EItemCategory category;
    @NotNull private int initPrice;
    private int soldPrice;
    @NotNull private LocalDateTime buyDate;
    @NotNull private int itemStatePoint;
    private String description;
    private List<String> fileNames;

    public static ItemDetailsResponse from(Item item) {
        List<String> fileNames = new ArrayList<>();
        if (item.getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = item.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        return ItemDetailsResponse.builder()
                .id(item.getId())
                .itemName(item.getItemName())
                .category(item.getCategory())
                .initPrice(item.getInitPrice())
                .soldPrice(item.getSoldPrice())
                .buyDate(item.getBuyDate())
                .itemStatePoint(item.getItemStatePoint())
                .description(item.getDescription())
                .fileNames(fileNames)
                .build();
    }
}
