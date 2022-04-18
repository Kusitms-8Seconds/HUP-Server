package eightseconds.domain.item.dto;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.myfile.entity.MyFile;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
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
@ApiModel(description = "상품들의 상세정보를 좋아요 순으로 조회하기 위한 응답 객체")
public class BestItemResponse {

    private Long id;
    private Long userId;
    private String itemName;
    private ECategory category;
    private int initPrice;
    private LocalDateTime buyDate;
    private int itemStatePoint;
    private String description;
    private ItemConstants.EItemSoldStatus soldStatus;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;
    private int heart;
    private int maximumPrice;

    public static BestItemResponse from(Item item, int heart) {
        List<String> fileNames = new ArrayList<>();
        if (item.getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = item.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFileKey());
            }
        }
        int maxPrice = 0;
        for (PriceSuggestion priceSuggestion : item.getPriceSuggestions()) {
            int suggestionPrice = priceSuggestion.getSuggestionPrice();
            if (maxPrice < suggestionPrice) {
                maxPrice = suggestionPrice;
            }
        }
        return BestItemResponse.builder()
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
                .heart(heart)
                .maximumPrice(maxPrice)
                .build();
    }
}
