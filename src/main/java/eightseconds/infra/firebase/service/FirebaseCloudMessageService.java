package eightseconds.infra.firebase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import eightseconds.infra.firebase.vo.FcmMessage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/auctionapp-f3805/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken, String title, String body, LocalDateTime nowLocalDateTime) throws IOException {
        String message = makeMessage(targetToken, title, body, nowLocalDateTime);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        Response response = client.newCall(request).execute();
    }

    private String makeMessage(String targetToken, String title, String body, LocalDateTime nowLocalDateTime) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .createdDateTime(nowLocalDateTime)
                                .image("https://firebasestorage.googleapis.com/v0/b/auctionapp-f3805.appspot.com/o/HUPIcon.jpg?alt=media&token=32b7c5b5-59b4-400c-adb8-5994c153d4e3")
                                .build()
                        ).build()).validateOnly(false).build();
        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
