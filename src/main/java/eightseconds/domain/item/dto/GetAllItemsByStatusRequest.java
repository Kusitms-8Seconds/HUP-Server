package eightseconds.domain.item.dto;

import eightseconds.domain.item.constant.ItemConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllItemsByStatusRequest {

    @NotNull private Long userId;
    @NotNull @Enumerated(EnumType.STRING) private ItemConstants.EItemSoldStatus soldStatus;

}
