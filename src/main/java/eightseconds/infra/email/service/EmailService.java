package eightseconds.infra.email.service;

import eightseconds.global.dto.DefaultResponse;
import eightseconds.infra.email.dto.EmailResetPasswordResponse;

public interface EmailService {
    DefaultResponse sendSimpleMessageForActivateUser(String email) throws Exception;
    DefaultResponse sendSimpleMessageForResetPassword(String email) throws Exception;
    DefaultResponse activateUserAuthCode(String emailAuthCode);
    EmailResetPasswordResponse resetPasswordAuthCode(String emailAuthCode);
}
