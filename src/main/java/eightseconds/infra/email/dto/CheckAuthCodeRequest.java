package eightseconds.infra.email.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "인증코드 검증을 위한 요청 객체")
public class CheckAuthCodeRequest {

    @NotBlank(message = "인증코드를 입력해주세요.")
    @Size(min = 8, max = 8, message = "인증코드는 8자리여야 합니다.")
    @ApiModelProperty(notes = "이메일로 전송된 인증코드를 입력해주세요.")
    private String authCode;
}
