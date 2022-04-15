package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "아이디를 찾기 위한 요청 객체")
public class FindLoginIdRequest {

    @NotBlank(message = "회원의 가입한 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    @ApiModelProperty(notes = "회원의 이메일을 입력해 주세요.")
    private String email;

}
