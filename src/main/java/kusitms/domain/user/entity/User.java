package kusitms.domain.user.entity;

import kusitms.domain.item.entity.Item;
import kusitms.domain.pricesuggestion.entity.PriceSuggestion;
import kusitms.domain.scrap.entity.Scrap;
import kusitms.global.entity.BaseEntity;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String username;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    private String address;

    @Column(name = "activated")
    private boolean activated;

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

    @Builder
    public User(String email, String username, String password, String phoneNumber, String address) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // 연관관계 메서드
    public void addItem(Item item) {
        this.items.add(item);
        item.setUser(this);
    }

}
