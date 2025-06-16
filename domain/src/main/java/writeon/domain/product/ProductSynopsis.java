package writeon.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writeon.domain.common.BaseAuditTimeEntity;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_synopsis")
public class ProductSynopsis extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "length")
    private String length;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "logline", nullable = false)
    private String logline;

    @Column(name = "example")
    private String example;

    @Builder
    public ProductSynopsis(UUID id, String genre, String length, String purpose, String logline, String example) {
        this.id = id;
        this.genre = genre;
        this.length = length;
        this.purpose = purpose;
        this.logline = logline;
        this.example = example;
    }

    public void update(String genre, String length, String purpose, String logline, String example) {
        this.genre = genre;
        this.length = length;
        this.purpose = purpose;
        this.logline = logline;
        this.example = example;
    }
}
