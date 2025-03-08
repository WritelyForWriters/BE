package writeon.domain.terms;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writely.tables.records.TermsRecord;
import writeon.domain.common.BaseTimeEntity;
import writeon.domain.terms.enums.TermsCode;

import java.util.UUID;

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
