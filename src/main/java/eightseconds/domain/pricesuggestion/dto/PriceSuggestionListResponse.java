package eightseconds.domain.pricesuggestion.dto;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PriceSuggestionListResponse {

    private Long priceSuggestionId;
    private Long userId;
    private String userName;
    private Long itemId;
    private String itemName;
    private int suggestionPrice;
    private boolean acceptState;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;

    public static PriceSuggestionListResponse from(PriceSuggestion priceSuggestion) {
        List<String> fileNames = new ArrayList<>();
        if (priceSuggestion.getItem().getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = priceSuggestion.getItem().getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFilename());
            }
        }
        return PriceSuggestionListResponse.builder()
                .priceSuggestionId(priceSuggestion.getId())
                .userId(priceSuggestion.getUser().getId())
                .userName(priceSuggestion.getItem().getUser().getUsername())
                .itemId(priceSuggestion.getItem().getId())
                .itemName(priceSuggestion.getItem().getItemName())
                .suggestionPrice(priceSuggestion.getSuggestionPrice())
                .acceptState(priceSuggestion.isAcceptState())
                .fileNames(fileNames)
                .auctionClosingDate(priceSuggestion.getItem().getAuctionClosingDate())
                .build();
    }
}
