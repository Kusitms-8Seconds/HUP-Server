package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "유저 프로필 수정을 위한 응답 객체")
public class UpdateProfileResponse {
    private String profileImageURL;

    public static UpdateProfileResponse from(String fileURL) {
        return UpdateProfileResponse.builder()
                .profileImageURL(fileURL)
                .build();
    }
}
