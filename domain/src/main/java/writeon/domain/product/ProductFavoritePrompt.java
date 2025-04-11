package writeon.domain.product;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_favorite_prompt")
public class ProductFavoritePrompt {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = Generators.timeBasedEpochGenerator().generate();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "messageId", nullable = false)
    private UUID messageId;

    @Column(name = "created_at", updatable = false, nullable = false)
    protected final LocalDateTime createdAt = LocalDateTime.now();

    public ProductFavoritePrompt(Product product, UUID messageId) {
        this.product = product;
        this.messageId = messageId;
    }
}
