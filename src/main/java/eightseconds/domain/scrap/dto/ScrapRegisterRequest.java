package eightseconds.domain.scrap.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapRegisterRequest {

    private Long userId;
    private Long itemId;
}
