package writeon.domain.product;

import com.fasterxml.uuid.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.common.BaseAuditTimeEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductCharacter> characters = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductCustomField> customFields = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductIdeaNote ideaNote;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<ProductMemo> memos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductPlot plot;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductSynopsis synopsis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private ProductWorldview worldview;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFavoritePrompt> favoritePrompts = new ArrayList<>();

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductFixedMessage fixedMessage;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deleteFavoritePrompt(ProductFavoritePrompt favoritePrompt) {
        this.favoritePrompts.remove(favoritePrompt);
    }

    public void setFixedMessage(ProductFixedMessage fixedMessage) {
        this.fixedMessage = fixedMessage;
        fixedMessage.setProduct(this);
    }

    public void deleteFixedMessage() {
        if (this.fixedMessage != null) {
            this.fixedMessage.setProduct(null);
            this.fixedMessage = null;
        }
    }
}
