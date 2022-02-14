package eightseconds.domain.notice.entity;

import eightseconds.domain.file.entity.MyFile;
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
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "notice")
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
        System.out.println("여기들어오는지1");
        for (MyFile file : files) {
            System.out.println("1");
            System.out.println("myfilesiaze"+myFiles.size());
            myFiles.add(file);
            System.out.println("2");
            file.setNotice(this);
            System.out.println("3");
        }
        System.out.println("여기들어오는지2");
    }

    public void setUser(User user) {
        this.user = user;
        user.getNotices().add(this);
    }

    public void updateNotice(String title, String body) {
        this.setTitle(title);
        this.setBody(body);
    }
}
