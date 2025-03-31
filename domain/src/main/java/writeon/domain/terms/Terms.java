package writeon.domain.terms;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writeon.domain.common.BaseTimeEntity;
import writeon.domain.terms.enums.TermsCode;
import writeon.tables.records.TermsRecord;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "terms")
public class Terms extends BaseTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Convert(converter = TermsCode.TypeCodeConverter.class)
    @Column(nullable = false)
    private String cd;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    public Terms(TermsRecord terms) {
        this.id = terms.getId();
    }

}
