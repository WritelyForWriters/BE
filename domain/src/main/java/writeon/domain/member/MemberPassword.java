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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member_password")
public class MemberPassword extends BaseTimeEntity {
    @Id
    @Column(name = "member_id")
    private UUID memberId;

    @Column(nullable = false)
    private String password;
}
