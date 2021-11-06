package eightseconds.domain.user.entity;

import eightseconds.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseTimeEntity {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
