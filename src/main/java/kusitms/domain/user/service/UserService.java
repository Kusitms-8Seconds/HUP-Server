package kusitms.domain.user.service;

import kusitms.domain.user.dto.SignUpRequest;
import kusitms.domain.user.entity.User;

public interface UserService {

    User saveUser(SignUpRequest signUpRequest);
}
