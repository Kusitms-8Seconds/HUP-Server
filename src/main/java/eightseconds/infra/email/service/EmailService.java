package eightseconds.infra.email.service;

import eightseconds.global.dto.DefaultResponse;

public interface EmailService {
    DefaultResponse sendSimpleMessage(String email) throws Exception;
    DefaultResponse checkAuthCode(String emailAuthCode);
}
