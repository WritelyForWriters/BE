package writeon.domain.product;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writeon.domain.common.BaseAuditTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_favorite_prompt")
public class ProductFavoritePrompt {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "prompt", nullable = false)
    private String prompt;

    @Column(name = "created_at", updatable = false, nullable = false)
    protected final LocalDateTime createdAt = LocalDateTime.now();

    public ProductFavoritePrompt(Product product, String prompt) {
        this.product = product;
        this.prompt = prompt;
    }
}
