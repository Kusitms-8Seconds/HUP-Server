package eightseconds.global.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime timeStamp;
    private String status;
    private List<String> messages;

    public ExceptionResponse(String status, List<String> messages) {
        this.timeStamp = LocalDateTime.now().withNano(0);
        this.status = status;
        this.messages = messages;
    }
}
