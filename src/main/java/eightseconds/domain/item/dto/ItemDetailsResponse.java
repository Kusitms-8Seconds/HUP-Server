package eightseconds.domain.item.dto;

import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.constant.ItemConstants.EItemSoldStatus;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
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
@ApiModel(description = "상품들의 상세정보를 조회하기 위한 응답 객체")
public class ItemDetailsResponse {

    @NotNull private Long id;
    @NotNull private Long userId;
    @NotNull private String itemName;
    @NotNull @Enumerated(EnumType.STRING) private ECategory category;
    @NotNull private int initPrice;
    private int soldPrice;
    @NotNull private LocalDateTime buyDate;
    @NotNull private int itemStatePoint;
    private String description;
    @NotNull @Enumerated(EnumType.STRING) private EItemSoldStatus soldStatus;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;
    private int scrapCount;
    private int maximumPrice;
    private int participants;
    private Long chatRoomId;
    private Long bidderUserId;
    private String bidderUserName;

    public static ItemDetailsResponse from(Item item) {
        List<String> fileNames = new ArrayList<>();
        if (item.getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = item.getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        int maxPrice = 0;
        for (PriceSuggestion priceSuggestion : item.getPriceSuggestions()) {
            int suggestionPrice = priceSuggestion.getSuggestionPrice();
            if (maxPrice < suggestionPrice) {
                maxPrice = suggestionPrice;
            }
        }
        Long chatRoomId = null;
        if (item.getChatRooms().size() != 0) {
            chatRoomId = item.getChatRooms().get(0).getId();
        }
        Long bidderUserId = null;
        String bidderUserName = null;
        if (item.getSoldStatus().equals(EItemSoldStatus.eSoldOut)) {
            for (PriceSuggestion priceSuggestion : item.getPriceSuggestions()) {
                if (priceSuggestion.getSuggestionPrice() == maxPrice) {
                    bidderUserId = priceSuggestion.getUser().getId();
                    bidderUserName = priceSuggestion.getUser().getUsername();
                }
            }
        }
        return ItemDetailsResponse.builder()
                .id(item.getId())
                .userId(item.getUser().getId())
                .itemName(item.getItemName())
                .category(item.getCategory().getCategory())
                .initPrice(item.getInitPrice())
                .soldPrice(item.getSoldPrice())
                .buyDate(item.getBuyDate())
                .itemStatePoint(item.getItemStatePoint())
                .description(item.getDescription())
                .soldStatus(item.getSoldStatus())
                .fileNames(fileNames)
                .auctionClosingDate(item.getAuctionClosingDate())
                .scrapCount(item.getScraps().size())
                .maximumPrice(maxPrice)
                .participants(item.getPriceSuggestions().size())
                .chatRoomId(chatRoomId)
                .bidderUserId(bidderUserId)
                .bidderUserName(bidderUserName)
                .build();
    }
}
