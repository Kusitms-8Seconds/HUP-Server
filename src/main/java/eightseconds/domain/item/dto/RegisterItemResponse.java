package eightseconds.domain.item.dto;

import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.myfile.entity.MyFile;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ApiModel(description = "상품등록을 위한 응답 객체")
public class RegisterItemResponse {

    private Long id;
    private Long userId;
    private final String itemName;
    private ECategory category;
    private int initPrice;
    private LocalDateTime buyDate;
    private int itemStatePoint;
    private String description;
    private EItemSoldStatus soldStatus;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;

    public static RegisterItemResponse from(Item item) {
        List<String> fileNames = new ArrayList<>();
        if (!item.getMyFiles().isEmpty()) {
            List<MyFile> myFiles = item.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFileKey());
            }
        }
        return RegisterItemResponse.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .itemName(item.getItemName())
                .category(item.getCategory().getCategory())
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
