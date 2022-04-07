package eightseconds.domain.user.entity;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import eightseconds.domain.chatmessage.entity.ChatMessage;
import eightseconds.domain.chatroom.entity.UserChatRoom;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.notice.entity.Notice;
import eightseconds.domain.notification.entity.Notification;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.user.constant.UserConstants;
import eightseconds.domain.user.constant.UserConstants.EGoogleUser;
import eightseconds.domain.user.constant.UserConstants.EKakaoUser;
import eightseconds.domain.user.constant.UserConstants.ENaverUser;
import eightseconds.domain.user.constant.UserConstants.EUser;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.domain.user.constant.UserConstants.EAuthority;
import eightseconds.domain.user.dto.SignUpRequest;
import eightseconds.domain.user.dto.UpdateUserRequest;
import eightseconds.global.entity.BaseTimeEntity;
import eightseconds.infra.email.entity.EmailAuth;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    @Column
    private String email;

    private String username;

    @Column
    private String password;

    private String phoneNumber;

    @Column(name = "activated")
    private boolean activated;

    private boolean emailAuthActivated;

    private String picture;

    @Enumerated(EnumType.STRING)
    private ELoginType loginType;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<PriceSuggestion> priceSuggestions = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "email_auth_id")
    private EmailAuth emailAuth;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Notification> notifications = new ArrayList<>();

    private String targetToken;


    /**
     * 연관관계 메서드
     */

    public void addItem(Item item) {
        this.items.add(item);
        //item.setUser(this);
    }

    public void updateNotice(Notice notice) {
        this.notices.add(notice);
        notice.setUser(this);
    }

    public void getAuthority(Long id){
        for (Iterator<Authority> itr = authorities.iterator(); itr.hasNext();) {
            Authority authority = itr.next();
        }
    }

    public User update(String username, String picture) {
        this.username = username;
        this.picture = picture;
        return this;
    }

    public static User toEntity(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder){
        return User.builder()
                .loginId(signUpRequest.getLoginId())
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phoneNumber(signUpRequest.getPhoneNumber())
                .authorities(Collections.singleton(toRoleUserAuthority()))
                .activated(true)
                .picture(UserConstants.EUserServiceImpl.eBasePicture.getValue())
                .loginType(ELoginType.eApp)
                .build();
    }


    public static User toGoogleUserEntity(Payload payload){
        return User.builder()
                .loginId(EGoogleUser.eGoogle.getValue()+payload.getEmail())
                .email(payload.getEmail())
                .username((String)payload.get(EGoogleUser.eGoogleNameAttribute.getValue()))
                .picture((String)payload.get(EGoogleUser.eGooglePictureAttribute.getValue()))
                .authorities(Collections.singleton(toRoleUserAuthority()))
                .activated(true)
                .emailAuthActivated(true)
                .loginType(ELoginType.eGoogle)
                .build();
    }

    public static User toKakaoUserEntity(HashMap<String, Object> userInfo) {
        return User.builder()
                .loginId(EKakaoUser.eKakao.getValue()+userInfo.get(EKakaoUser.eKakaoEmailAttribute.getValue()).toString())
                .email(userInfo.get(EKakaoUser.eKakaoEmailAttribute.getValue()).toString())
                .username(userInfo.get(EKakaoUser.eKakaoNickNameAttribute.getValue()).toString())
                .picture(userInfo.get(EKakaoUser.eKakaoProfile.getValue()).toString())
                .authorities(Collections.singleton(toRoleUserAuthority()))
                .activated(true)
                .emailAuthActivated(true)
                .loginType(ELoginType.eKakao)
                .build();
    }

    public static User toEntityOfNaverUser(HashMap<String, Object> userInfo) {
        return User.builder()
                .loginId(ENaverUser.eNaver.getValue()+userInfo.get(ENaverUser.eNaverEmailAttribute.getValue()).toString())
                .email(userInfo.get(ENaverUser.eNaverEmailAttribute.getValue()).toString())
                .username(userInfo.get(ENaverUser.eNaverNameAttribute.getValue()).toString())
                .picture(userInfo.get(ENaverUser.eNaverProfileImageAttribute.getValue()).toString())
                .authorities(Collections.singleton(toRoleUserAuthority()))
                .activated(true)
                .emailAuthActivated(true)
                .loginType(ELoginType.eNaver)
                .build();
    }

    private static Authority toRoleUserAuthority() {
        return Authority.builder()
                .authorityName(EAuthority.eRoleUser.getValue())
                .build();
    }

    public User updateUserByUpdateUserRequest(UpdateUserRequest userRequest) {
        setLoginId(userRequest.getLoginId());
        setUsername(userRequest.getUsername());
        setPassword(userRequest.getPassword());
        setPhoneNumber(userRequest.getPhoneNumber());
        return this;
    }

}
