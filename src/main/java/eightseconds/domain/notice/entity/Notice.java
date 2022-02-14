package eightseconds.domain.notice.entity;

import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.user.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Notice {

    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "file", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<MyFile> myFiles = new ArrayList<>();

    /**
     * 연관관계 메서드
     */

    public void addFiles(List<MyFile> files){
        for (MyFile file : files) {
            myFiles.add(file);
            file.setNotice(this);
        }
    }

    public static Notice createNotice(User user, String title, String body, List<MultipartFile> files) {
        return Notice.builder()
                .title(title)
                .body(body)
                .build();
    }
}
