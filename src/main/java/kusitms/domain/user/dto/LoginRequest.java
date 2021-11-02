package kusitms.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    @Size(min = 5, max = 11)
    private String loginId;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;
}
