package writeon.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import writeon.domain.common.BaseAuditTimeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();

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

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
