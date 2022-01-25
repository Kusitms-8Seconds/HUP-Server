package eightseconds.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원 정보 수정을 위한 업데이트 요청 객체")
public class UpdateUserRequest {

    @NotNull(message = "회원의 유저 Id를 입력해주세요.")
    private Long userId;

    @NotBlank(message = "수정하고자 하는 로그인 Id를 입력해주세요.")
    @Size(min = 5, max = 11, message = "수정하고자 하는 로그인 Id는 크기가 5에서 11사이여야 합니다.")
    @ApiModelProperty(notes = "수정하고자 하는 로그인 Id를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "수정하고자 하는 이름을 입력해 주세요.")
    @Size(min = 2, max = 10, message = "수정하고자 하는 이름은 2글자 이상 10글자 이하여야 합니다.")
    @ApiModelProperty(notes = "수정하고자 하는 회원 이름을 입력해 주세요.")
    private String username;

    @NotBlank(message = "수정하고자 하는 비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 16, message = "수정하고자 하는 패스워드는 8글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "수정하고자 하는 비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "수정하고자 하는 전화번호를 입력해주세요.")
    @ApiModelProperty(notes = "수정하고자 하는 전화번호를 입력해 주세요.")
    private String phoneNumber;

}
