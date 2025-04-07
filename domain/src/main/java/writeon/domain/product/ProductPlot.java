package writeon.domain.product;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writeon.domain.common.BaseAuditTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_plot")
public class ProductPlot extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column
    private String content;

    @Builder
    public ProductPlot(UUID id, String content) {
        this.id = id;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
