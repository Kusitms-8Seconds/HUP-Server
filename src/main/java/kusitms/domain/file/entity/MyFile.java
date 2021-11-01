package kusitms.domain.file.entity;

import kusitms.domain.item.entity.Item;
import kusitms.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Entity(name = "file")
public class MyFile extends BaseEntity {

    @Getter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String filename;
    private String fileOriginName;
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public MyFile(String filename, String fileOriginName, String fileUrl) {
        this.filename = filename;
        this.fileOriginName = fileOriginName;
        this.fileUrl = fileUrl;
    }
}