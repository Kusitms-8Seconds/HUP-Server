package eightseconds.infra.email.entity;

import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
import eightseconds.infra.email.constant.EmailConstants.EEmailAuth;
import eightseconds.infra.email.exception.ExpiredAuthCodeTimeException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
public class EmailAuth extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "email_auth_id")
    private Long id;

    private String email;

    private String authCode;

    @OneToOne(mappedBy = "emailAuth", fetch = FetchType.LAZY)
    private User user;

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
        user.setEmailAuth(this);
    }

    // 생성 메서드
    public static EmailAuth createEmailAuth(String email, String authCode, User user) {
        EmailAuth emailAuth = new EmailAuth();
        emailAuth.setEmail(email);
        emailAuth.setAuthCode(authCode);
        emailAuth.setUser(user);
        return emailAuth;
    }

    /**
     * validate
     */

    public void validateValidPeriod(EmailAuth emailAuth) {
        LocalDateTime createdDateTime = emailAuth.getCreatedDate();
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (createdDateTime.until(currentDateTime, ChronoUnit.MINUTES) > EEmailAuth.eEmailValidationTime.getMinutes()) throw new ExpiredAuthCodeTimeException(
                EEmailAuth.eExpiredAuthCodeTimeExceptionMessage.getValue());
    }
}
