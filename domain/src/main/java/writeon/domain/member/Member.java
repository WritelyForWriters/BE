package writeon.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import writeon.domain.common.BaseTimeEntity;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

}
