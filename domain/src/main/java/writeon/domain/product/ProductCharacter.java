package writeon.domain.product;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import writeon.domain.common.BaseAuditTimeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_character")
public class ProductCharacter extends BaseAuditTimeEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "intro")
    private String intro;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Short age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "appearance")
    private String appearance;

    @Column(name = "personality")
    private String personality;

    @Column(name = "relationship")
    private String relationship;

    @Column(name = "seq", nullable = false)
    private Integer seq;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    @SQLRestriction("section_type = 'character'")
    private List<ProductCustomField> customFields = new ArrayList<>();

    @Builder
    public ProductCharacter(UUID productId, String intro, String name, Short age, String gender, String occupation, String appearance, String personality, String relationship, Integer seq) {
        this.productId = productId;
        this.intro = intro;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.appearance = appearance;
        this.personality = personality;
        this.relationship = relationship;
        this.seq = seq;
    }

    public void update(String intro, String name, Short age, String gender, String occupation, String appearance, String personality, String relationship, Integer seq) {
        this.intro = intro;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.occupation = occupation;
        this.appearance = appearance;
        this.personality = personality;
        this.relationship = relationship;
        this.seq = seq;
    }
}
