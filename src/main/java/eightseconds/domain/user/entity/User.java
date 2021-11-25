package eightseconds.domain.user.entity;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import eightseconds.domain.item.entity.Item;
import eightseconds.domain.pricesuggestion.entity.PriceSuggestion;
import eightseconds.domain.scrap.entity.Scrap;
import eightseconds.domain.user.constant.UserConstants.ELoginType;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;
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

    // oAuth2관련
    private String picture;
    @Enumerated(EnumType.STRING)
    private ELoginType loginType;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "user")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PriceSuggestion> priceSuggestions = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<ChatRoom> chatRooms = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<ChatMessage> chatMessages = new ArrayList<>();



    // 연관관계 메서드
    public void addItem(Item item) {
        this.items.add(item);
        item.setUser(this);
    }

    public void getAuthority(Long id){
        for (Iterator<Authority> itr = authorities.iterator(); itr.hasNext();) {
            Authority authority = itr.next();
            //if(id = authority.get)
        }
    }

//    // 연관관계 메서드
//    public void addAuthorities(){
//        this.authorities
//    }

//    @Builder
//    public User(String loginId, String email, String username,
//                String password, String phoneNumber, Set<Authority> authorities) {
//        this.loginId = loginId;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//        this.phoneNumber = phoneNumber;
//        this.description = description;
//    }

    // Google Builder

    // Google Update Profile
    public User update(String username, String picture) {
        this.username = username;
        this.picture = picture;
        return this;
    }

    public static User toEntity(OAuth2User oAuth2User) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return User.builder()
                .email((String)attributes.get("email"))
                .username((String)attributes.get("name"))
                .picture((String)attributes.get("picture"))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .loginType(ELoginType.eApp)
                .build();
    }

    public static User toEntityOfGoogleUser(Payload payload){
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return User.builder()
                .email(payload.getEmail())
                .username((String)payload.get("name"))
                .picture((String)payload.get("picture"))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .loginType(ELoginType.eGoogle)
                .build();
    }

    public static User toEntityOfKakaoUser(HashMap<String, Object> userInfo) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return User.builder()
                .email(userInfo.get("email").toString())
                .username(userInfo.get("nickname").toString())
                .picture(userInfo.get("profile_image").toString())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .loginType(ELoginType.eKakao)
                .build();
    }

    public static User toEntityOfNaverUser(HashMap<String, Object> userInfo) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        return User.builder()
                .email(userInfo.get("email").toString())
                .username(userInfo.get("name").toString())
                .picture(userInfo.get("profile_image").toString())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .loginType(ELoginType.eNaver)
                .build();
    }

}
