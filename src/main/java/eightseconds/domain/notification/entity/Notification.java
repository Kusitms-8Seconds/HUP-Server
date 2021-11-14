package eightseconds.domain.notification.entity;

import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
    //private
}
