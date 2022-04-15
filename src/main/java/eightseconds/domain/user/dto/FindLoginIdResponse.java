package eightseconds.domain.user.dto;

import eightseconds.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "아이디 찾기를 위한 응답 객체")
public class FindLoginIdResponse {
    private Long userId;
    private String loginId;
    public static FindLoginIdResponse from(User user) {
        return FindLoginIdResponse.builder()
                .userId(user.getId())
                .loginId(user.getLoginId())
                .build();
    }
}
