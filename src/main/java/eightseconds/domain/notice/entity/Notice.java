package eightseconds.domain.notice.entity;

import eightseconds.domain.myfile.entity.MyFile;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "notice", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<MyFile> myFiles = new ArrayList<>();

    @Builder
    public Notice(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public static Notice toEntity(String title, String body) {
        return Notice.builder()
                .title(title)
                .body(body)
                .build();
    }

    /**
     * 연관관계 메서드
     */

    public void addFiles(List<MyFile> files){
        for (MyFile file : files) {
            myFiles.add(file);
            file.setNotice(this);
        }
    }

    public void setUser(User user) {
        this.user = user;
        user.getNotices().add(this);
    }

    public void updateNotice(String title, String body) {
        this.setTitle(title);
        this.setBody(body);
    }

    public void deleteUserAndNotice(Notice notice) {
        notice.getUser().getNotices().remove(notice);
        this.user = null;
    }
}
