package eightseconds.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResultResponse {
    private String code;
    private String message;
}
