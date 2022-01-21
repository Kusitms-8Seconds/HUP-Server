package eightseconds.domain.item.dto;

import eightseconds.domain.item.constant.ItemConstants.EItemCategory;
import eightseconds.domain.item.entity.Item;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "상품등록을 위한 요청 객체")
public class RegisterItemRequest {

    @NotBlank(message = "등록하고자 하는 상품의 이름을 입력해주세요.")
    @ApiModelProperty(notes = "상품 이름을 입력해 주세요.")
    private String itemName;

    @NotBlank(message = "등록하고자 하는 상품의 카테고리를 입력해주세요.")
    @ApiModelProperty(notes = "상품 카테고리를 입력해 주세요.")
    private EItemCategory category;

    @NotBlank(message = "등록하고자 하는 상품의 초기가격을 입력해주세요.")
    @ApiModelProperty(notes = "상품의 초기가격을 입력해 주세요.")
    private int initPrice;

    @NotBlank(message = "등록하고자 하는 상품의 구매날짜를 입력해주세요.")
    @ApiModelProperty(notes = "상품의 구매날짜를 입력해 주세요.")
    private LocalDateTime buyDate;

    @NotBlank(message = "등록하고자 하는 상품의 상태점수를 입력해주세요.")
    @ApiModelProperty(notes = "상품의 상태점수를 입력해 주세요.")
    private int itemStatePoint;

    @NotBlank(message = "등록하고자 하는 상품의 설명을 입력해주세요.")
    @ApiModelProperty(notes = "상품의 설명을 입력해 주세요.")
    private String description;

    @NotBlank(message = "등록하고자 하는 상품의 경매종료일자를 입력해주세요.")
    @ApiModelProperty(notes = "상품의 경매종료일자를 입력해 주세요.")
    private LocalDateTime auctionClosingDate;

    @NotBlank(message = "등록하고자 하는 상품의 이미지를 입력해주세요.")
    @ApiModelProperty(notes = "상품의 이미지를 입력해 주세요.")
    private List<MultipartFile> files;

    public static RegisterItemRequest of(String itemName, String category, String initPrice, String buyDate, String itemStatePoint
            , String description, String auctionClosingDate, List<MultipartFile> files) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime buyDateFormatted = LocalDateTime.parse(buyDate, formatter);
        LocalDateTime auctionClosingDateFormatted = LocalDateTime.parse(auctionClosingDate, formatter);

        return RegisterItemRequest.builder()
                .itemName(itemName)
                .category(EItemCategory.valueOf(category))
                .initPrice(Integer.valueOf(initPrice))
                .buyDate(buyDateFormatted)
                .itemStatePoint(Integer.valueOf(itemStatePoint))
                .description(description)
                .auctionClosingDate(auctionClosingDateFormatted)
                .files(files)
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
