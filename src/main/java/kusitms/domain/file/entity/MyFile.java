package kusitms.domain.file.entity;

import kusitms.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "file")
public class MyFile {

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