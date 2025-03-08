package writeon.domain.terms;

import jakarta.persistence.*;
import lombok.*;
import writeon.domain.common.BaseTimeEntity;
import writeon.domain.terms.enums.TermsCode;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "terms_agreement")
@IdClass(TermsAgreementId.class)
public class TermsAgreement extends BaseTimeEntity {

    @Id
    @Column(name = "terms_cd", nullable = false)
    private TermsCode termsCd;

    @Id
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

}
