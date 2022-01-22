package eightseconds.infra.email.service;

import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.constant.UserConstants.EUserServiceImpl;
import eightseconds.domain.user.entity.User;
import eightseconds.domain.user.exception.NotFoundUserException;
import eightseconds.domain.user.repository.UserRepository;
import eightseconds.global.dto.DefaultResponse;
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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final EmailAuthRepository emailAuthRepository;
    public String authCode;

    @Override
    public DefaultResponse sendSimpleMessage(String email) throws Exception {

        User user = userRepository.findByEmailAndLoginType(email, ELoginType.eApp).orElseThrow(()
                -> new NotFoundUserException(EUserServiceImpl.eNotFoundUserException.getValue()));

        MimeMessage message = createMessage(email);
        try{
            emailSender.send(message);
            EmailAuth emailAuth = EmailAuth.createEmailAuth(email, authCode, user);
            emailAuthRepository.save(emailAuth);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return DefaultResponse.from("이메일로 인증코드를 보냈습니다.");
    }

    @Override
    @Transactional
    public DefaultResponse checkAuthCode(String authCode) {
        EmailAuth emailAuth = emailAuthRepository.findByAuthCode(authCode).orElseThrow(() -> new InvalidAuthCodeException("잘못된 인증코드 입니다."));
        emailAuth.validateValidPeriod(emailAuth);
        emailAuth.getUser().setEmailAuthActivated(true);
        return DefaultResponse.from("인증이 완료되었습니다.");
    }

    private MimeMessage createMessage(String to) throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ authCode);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("HUP회원가입 이메일 인증");//제목

        authCode = createKey();

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 HUP입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= authCode+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hup.contact.auth@gmail.com","HUP"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    /**
     * validate
     */

}
