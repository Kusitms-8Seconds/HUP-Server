package eightseconds.domain.item.dto;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RegisterItemResponse {

    private Long id;
    private Long userId;
    private final String itemName;
    private EItemCategory category;
    private int initPrice;
    private LocalDateTime buyDate;
    private int itemStatePoint;
    private String description;
    private EItemSoldStatus soldStatus;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;

    public static RegisterItemResponse from(Item item) {
        List<String> fileNames = new ArrayList<>();
        if (item.getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = item.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        return RegisterItemResponse.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .itemName(item.getItemName())
                .category(item.getCategory())
                .initPrice(item.getInitPrice())
                .buyDate(item.getBuyDate())
                .itemStatePoint(item.getItemStatePoint())
                .description(item.getDescription())
                .soldStatus(item.getSoldStatus())
                .auctionClosingDate(item.getAuctionClosingDate())
                .fileNames(fileNames)
                .build();
    }
}
