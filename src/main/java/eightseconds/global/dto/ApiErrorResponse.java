package eightseconds.global.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ApiErrorResponse {

    private LocalDateTime timeStamp;
    private String errorCode;
    private String message;

    public ApiErrorResponse(String errorCode, String message) {
        this.timeStamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.message = message;
    }
}