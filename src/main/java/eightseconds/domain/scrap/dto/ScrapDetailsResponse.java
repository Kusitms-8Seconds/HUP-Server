package eightseconds.domain.scrap.dto;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.scrap.entity.Scrap;
import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "유저의 스크랩 내역을 조회하는 응답 객체")
public class ScrapDetailsResponse {

    @NotNull private Long id;
    @NotNull private Long userId;
    @NotNull private Long itemId;
    @NotNull private String itemName;
    @NotNull @Enumerated(EnumType.STRING) private ECategory category;
    @NotNull private int initPrice;
    private int soldPrice;
    @NotNull private LocalDateTime buyDate;
    @NotNull private int itemStatePoint;
    private String description;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;
    private int maximumPrice;

    public static ScrapDetailsResponse from(Scrap scrap) {
        List<String> fileNames = new ArrayList<>();
        if (scrap.getItem().getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = scrap.getItem().getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        int maxPrice = 0;
        for (PriceSuggestion priceSuggestion : scrap.getItem().getPriceSuggestions()) {
            int suggestionPrice = priceSuggestion.getSuggestionPrice();
            if (maxPrice < suggestionPrice) {
                maxPrice = suggestionPrice;
            }
        }
        return ScrapDetailsResponse.builder()
                .id(scrap.getId())
                .userId(scrap.getUser().getId())
                .itemId(scrap.getItem().getId())
                .itemName(scrap.getItem().getItemName())
                .category(scrap.getItem().getCategory().getCategory())
                .initPrice(scrap.getItem().getInitPrice())
                .soldPrice(scrap.getItem().getSoldPrice())
                .buyDate(scrap.getItem().getBuyDate())
                .itemStatePoint(scrap.getItem().getItemStatePoint())
                .description(scrap.getItem().getDescription())
                .fileNames(fileNames)
                .auctionClosingDate(scrap.getItem().getAuctionClosingDate())
                .maximumPrice(maxPrice)
                .build();
    }
}
