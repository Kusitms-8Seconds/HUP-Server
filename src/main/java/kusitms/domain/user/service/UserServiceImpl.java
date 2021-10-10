package kusitms.domain.user.service;

import kusitms.domain.user.dto.SignUpRequest;
import kusitms.domain.user.entity.User;
import kusitms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(SignUpRequest signUpRequest) {
        return this.userRepository.save(signUpRequest.toEntity());
    }
}
