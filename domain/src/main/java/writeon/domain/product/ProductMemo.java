package writeon.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import writeon.domain.common.BaseAuditTimeEntity;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_memo")
public class ProductMemo extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "selected_text", nullable = false)
    private String selectedText;

    @Column(name = "start_index", nullable = false)
    private Integer startIndex;

    @Column(name = "endIndex", nullable = false)
    private Integer endIndex;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = Boolean.FALSE;

    public ProductMemo(UUID productId, String content, String selected_text,
                       Integer startIndex, Integer endIndex) {
        this.productId = productId;
        this.content = content;
        this.selectedText = selected_text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void update(String content, String selectedText, Integer startIndex, Integer endIndex, Boolean isCompleted) {
        this.content = content;
        this.selectedText = selectedText;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.isCompleted = isCompleted;
    }
}
