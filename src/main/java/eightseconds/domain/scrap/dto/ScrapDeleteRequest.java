package eightseconds.domain.scrap.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapDeleteRequest {

    private Long userId;
    private Long itemId;
    private Long scrapId;
}
