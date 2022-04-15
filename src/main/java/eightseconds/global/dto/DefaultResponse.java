package eightseconds.global.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DefaultResponse {

    private String message;
    public static DefaultResponse from(String message) {
        return new DefaultResponse(message);
    }
}