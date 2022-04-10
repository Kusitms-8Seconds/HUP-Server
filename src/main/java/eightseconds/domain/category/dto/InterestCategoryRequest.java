package eightseconds.domain.category.dto;

import eightseconds.domain.category.constant.CategoryConstants.ECategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "해당 유저의 관심 카테고리 설정 요청 객체")
public class InterestCategoryRequest {

    @NotNull(message = "userId를 입력해주세요.")
    @ApiModelProperty(notes = "userId를 입력해 주세요.")
    private Long userId;

    @NotNull(message = "관심 카테고리를 입력해주세요.")
    @ApiModelProperty(notes = "설정할 관심 카테고리를 입력해주세요.")
    private List<ECategory> eCategoryList;
}
