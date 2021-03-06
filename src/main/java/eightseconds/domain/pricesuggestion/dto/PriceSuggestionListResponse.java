package eightseconds.domain.pricesuggestion.dto;

import eightseconds.domain.item.constant.ItemConstants;
import eightseconds.domain.myfile.entity.MyFile;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
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
@ApiModel(description = "해당 유저의 경매 참여내역을 조회하기 위한 응답 객체")
public class PriceSuggestionListResponse {

    private Long priceSuggestionId;
    private Long userId;
    private String userName;
    private String picture;
    private Long itemId;
    private String itemName;
    private int suggestionPrice;
    private boolean acceptState;
    private List<String> fileNames;
    private LocalDateTime auctionClosingDate;
    private Long chatRoomId;
    private Long sellerUserId;
    private String sellerUserName;

    public static PriceSuggestionListResponse from(PriceSuggestion priceSuggestion) {
        List<String> fileNames = new ArrayList<>();
        if (priceSuggestion.getItem().getMyFiles().isEmpty() != true) {
            List<MyFile> myFiles = priceSuggestion.getItem().getMyFiles();
            fileNames = new ArrayList<>();
            for (MyFile myFile : myFiles) {
                fileNames.add(myFile.getFileKey());
            }
        }
        Long chatRoomId = null;
        Long sellerUserId = null;
        String sellerUserName = null;
        if (priceSuggestion.getItem().getSoldStatus().equals(ItemConstants.EItemSoldStatus.eSoldOut) &&
        priceSuggestion.getSuggestionPrice() == priceSuggestion.getItem().getSoldPrice()) {
                    chatRoomId = priceSuggestion.getItem().getChatRooms().get(0).getId();
                    sellerUserId = priceSuggestion.getItem().getUser().getId();
                    sellerUserName = priceSuggestion.getItem().getUser().getUsername();
        }
        return PriceSuggestionListResponse.builder()
                .priceSuggestionId(priceSuggestion.getId())
                .userId(priceSuggestion.getUser().getId())
                .userName(priceSuggestion.getUser().getUsername())
                .picture(priceSuggestion.getUser().getPicture())
                .itemId(priceSuggestion.getItem().getId())
                .itemName(priceSuggestion.getItem().getItemName())
                .suggestionPrice(priceSuggestion.getSuggestionPrice())
                .acceptState(priceSuggestion.isAcceptState())
                .fileNames(fileNames)
                .auctionClosingDate(priceSuggestion.getItem().getAuctionClosingDate())
                .chatRoomId(chatRoomId)
                .sellerUserId(sellerUserId)
                .sellerUserName(sellerUserName)
                .build();
    }
}
