package eightseconds.domain.myfile.entity;

import eightseconds.domain.item.entity.Item;
import eightseconds.domain.notice.entity.Notice;
import eightseconds.domain.user.entity.User;
import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myFileId;
    private String fileKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    public static MyFile toEntity(String key) {
        return MyFile.builder()
                .fileKey(key)
                .build();
    }
}
