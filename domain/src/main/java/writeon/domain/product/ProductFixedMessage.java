package writeon.domain.product;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writeon.domain.assistant.AssistantMessage;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_fixed_message")
public class ProductFixedMessage {

    @Id
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false, updatable = false)
    private AssistantMessage message;

    public ProductFixedMessage(AssistantMessage message) {
        this.message = message;
    }
}
