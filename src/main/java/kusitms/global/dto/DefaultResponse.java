package kusitms.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultResponse {

    private LocalDateTime responseTime;
    private String message;

    private DefaultResponse(String message) {
        this.responseTime = LocalDateTime.now();
        this.message = message;
    }

    public static DefaultResponse from(String message) {
        return new DefaultResponse(message);
    }

}