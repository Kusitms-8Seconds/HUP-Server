package eightseconds.infra.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private String targetToken;
    private String title;
    private String body;
}
