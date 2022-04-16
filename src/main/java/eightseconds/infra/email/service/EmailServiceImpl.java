package eightseconds.infra.email.service;

import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.service.UserServiceImpl;
import eightseconds.global.dto.DefaultResponse;
import eightseconds.infra.email.constant.EmailConstants.EEmailServiceImpl;
import eightseconds.infra.email.dto.EmailResetPasswordResponse;
import eightseconds.infra.email.entity.EmailAuth;
import eightseconds.infra.email.exception.InvalidAuthCodeException;
import eightseconds.infra.email.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;
    private final UserServiceImpl userService;
    private final EmailAuthRepository emailAuthRepository;
    public String authCode;

    @Override
    public DefaultResponse sendSimpleMessageForActivateUser(String email) throws Exception {

        User user = userService.validateNotRegisteredEmail(email);
        userService.validateIsAlreadyRegisteredUser(user);
        validateIsAlreadyExistEmail(email);

        MimeMessage message = createMessageForActivateUser(email);
        try{
            emailSender.send(message);
            EmailAuth emailAuth = EmailAuth.createEmailAuth(email, authCode, user);
            emailAuthRepository.save(emailAuth);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return DefaultResponse.from(EEmailServiceImpl.eSendAuthCodeMessage.getValue());
    }


    @Override
    public DefaultResponse sendSimpleMessageForResetPassword(String email) throws Exception {
        User user = userService.validateNotRegisteredEmail(email);
        validateIsAlreadyExistEmail(email);
        MimeMessage message = createMessageForResetPassword(email);
        try{
            emailSender.send(message);
            EmailAuth emailAuth = EmailAuth.createEmailAuth(email, authCode, user);
            emailAuthRepository.save(emailAuth);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return DefaultResponse.from(EEmailServiceImpl.eSendAuthCodeMessage.getValue());
    }

    public EmailAuth checkAuthCode(String authCode) {
        EmailAuth emailAuth = emailAuthRepository.findByAuthCode(authCode)
                .orElseThrow(() -> new InvalidAuthCodeException(EEmailServiceImpl.eInvalidAuthCodeExceptionMessage.getValue()));
        emailAuth.validateValidPeriod(emailAuth);
        return emailAuth;
    }

    @Override
    @Transactional
    public EmailResetPasswordResponse resetPasswordAuthCode(String authCode) {
        EmailAuth emailAuth = checkAuthCode(authCode);
        emailAuth.setPasswordCheck(true);
        return EmailResetPasswordResponse.from(emailAuth.getUser(), EEmailServiceImpl.eCompleteAuthMessage.getValue());
    }

    @Override
    @Transactional
    public DefaultResponse activateUserAuthCode(String authCode) {
        activateEmailUserActivate(checkAuthCode(authCode));
        return DefaultResponse.from(EEmailServiceImpl.eCompleteAuthMessage.getValue());
    }

    private void activateEmailUserActivate(EmailAuth emailAuth) {
        emailAuth.completedAuth();
    }

    private MimeMessage createMessageForActivateUser(String to) throws Exception{
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject(EEmailServiceImpl.eSubject.getValue());

        authCode = createKey();

        String msgg=EEmailServiceImpl.eEmpty.getValue();
        msgg+= EEmailServiceImpl.eSendMessage1.getValue();
        msgg+= EEmailServiceImpl.eSendMessage2.getValue();
        msgg+= EEmailServiceImpl.eSendMessage3.getValue();
        msgg+= EEmailServiceImpl.eSendMessage41.getValue();
        msgg+= EEmailServiceImpl.eSendMessage5.getValue();
        msgg+= EEmailServiceImpl.eSendMessage6.getValue();
        msgg+= EEmailServiceImpl.eSendMessage7.getValue();
        msgg+= EEmailServiceImpl.eSendMessage8.getValue();
        msgg+= EEmailServiceImpl.eSendMessage91.getValue();
        msgg+= EEmailServiceImpl.eSendMessage10.getValue();
        msgg+= EEmailServiceImpl.eSendMessage11.getValue();
        msgg+= authCode + EEmailServiceImpl.eSendMessage12.getValue();
        msgg+= EEmailServiceImpl.eSendMessage13.getValue();
        message.setText(msgg, EEmailServiceImpl.eSendMessageCharset.getValue(), EEmailServiceImpl.eSendMessageSubType.getValue());//내용
        message.setFrom(new InternetAddress(EEmailServiceImpl.eSendMessageAddress.getValue(),EEmailServiceImpl.eSendMessagePersonal.getValue()));//보내는 사람

        return message;
    }

    private MimeMessage createMessageForResetPassword(String to) throws Exception{
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject(EEmailServiceImpl.eSubject.getValue());

        authCode = createKey();

        String msgg=EEmailServiceImpl.eEmpty.getValue();
        msgg+= EEmailServiceImpl.eSendMessage1.getValue();
        msgg+= EEmailServiceImpl.eSendMessage2.getValue();
        msgg+= EEmailServiceImpl.eSendMessage3.getValue();
        msgg+= EEmailServiceImpl.eSendMessage42.getValue();
        msgg+= EEmailServiceImpl.eSendMessage5.getValue();
        msgg+= EEmailServiceImpl.eSendMessage6.getValue();
        msgg+= EEmailServiceImpl.eSendMessage7.getValue();
        msgg+= EEmailServiceImpl.eSendMessage8.getValue();
        msgg+= EEmailServiceImpl.eSendMessage92.getValue();
        msgg+= EEmailServiceImpl.eSendMessage10.getValue();
        msgg+= EEmailServiceImpl.eSendMessage11.getValue();
        msgg+= authCode + EEmailServiceImpl.eSendMessage12.getValue();
        msgg+= EEmailServiceImpl.eSendMessage13.getValue();
        message.setText(msgg, EEmailServiceImpl.eSendMessageCharset.getValue(), EEmailServiceImpl.eSendMessageSubType.getValue());//내용
        message.setFrom(new InternetAddress(EEmailServiceImpl.eSendMessageAddress.getValue(),EEmailServiceImpl.eSendMessagePersonal.getValue()));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = EEmailServiceImpl.eZero.getSize(); i < EEmailServiceImpl.eEight.getSize(); i++) { // 인증코드 8자리
            int index = rnd.nextInt(EEmailServiceImpl.eThree.getSize()); // 0~2 까지 랜덤
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(EEmailServiceImpl.eTwentySix.getSize())) + EEmailServiceImpl.eNinetySeven.getSize()));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(EEmailServiceImpl.eTwentySix.getSize())) + EEmailServiceImpl.eSixtyFive.getSize()));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(EEmailServiceImpl.eTen.getSize())));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    /**
     * validate email
     */

    private void validateIsAlreadyExistEmail(String email) {
        Optional<EmailAuth> findEmail = emailAuthRepository.findByEmail(email);
        if(findEmail.isPresent()) emailAuthRepository.delete(findEmail.get());
    }

}
