package writeon.api.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.domain.product.*;
import writeon.domain.product.enums.ProductSectionType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductTemplateSaveRequest {

    private List<Character> characters;
    private IdeaNote ideaNote;
    private Plot plot;
    private Synopsis synopsis;
    private Worldview worldview;

    @Getter
    @Setter
    public static class Character {

        @Schema(nullable = true)
        private UUID id;
        @Schema(title = "소개", nullable = true, example = "미국 최고의 기업가")
        private String intro;
        @Schema(title = "이름", nullable = true, example = "CEO")
        private String name;
        @Schema(title = "나이", nullable = true, example = "40")
        private Short age;
        @Schema(title = "성별", nullable = true, example = "남성")
        private String gender;
        @Schema(title = "직업", nullable = true, example = "CEO")
        private String occupation;
        @Schema(title = "외모", nullable = true, example = "평범")
        private String appearance;
        @Schema(title = "성격", nullable = true, example = "다정함")
        private String personality;
        @Schema(title = "특징", nullable = true)
        private String characteristic;
        @Schema(title = "주요관계", nullable = true)
        private String relationship;
        @Schema(title = "커스텀 필드", nullable = true)
        private List<CustomField> customFields;

        public ProductCharacter toEntity(UUID productId) {
            return ProductCharacter.builder()
                .productId(productId)
                .intro(intro)
                .name(name)
                .age(age)
                .gender(gender)
                .occupation(occupation)
                .appearance(appearance)
                .personality(personality)
                .relationship(relationship)
                .build();
        }
    }

    @Getter
    @Setter
    public static class CustomField {

        @Schema(nullable = true)
        private UUID id;
        @Schema(title = "필드 이름")
        private String name;
        @Schema(title = "필드 내용")
        private String content;

        public ProductCustomField toEntity(UUID productId, UUID sectionId, ProductSectionType sectionType) {
            return ProductCustomField.builder()
                .productId(productId)
                .sectionId(sectionId)
                .sectionType(sectionType)
                .name(name)
                .content(content)
                .seq((short) 1)
                .build();
        }
    }

    @Getter
    @Setter
    public static class IdeaNote {

        @Schema(title = "제목")
        private String title;
        @Schema(title = "내용", nullable = true)
        private String content;
    }

    @Getter
    @Setter
    public static class Plot {

        @Schema(title = "내용", nullable = true)
        private String content;

        public ProductPlot toEntity(UUID productId) {
            return ProductPlot.builder()
                .id(productId)
                .content(content)
                .build();
        }
    }

    @Getter
    @Setter
    public static class Synopsis {

        @Schema(title = "장르", example = "SF, 로맨스")
        private String genre;
        @Schema(title = "분량", nullable = true)
        private String length;
        @Schema(title = "기획 의도", nullable = true)
        private String purpose;
        @Schema(title = "로그라인")
        private String logline;
        @Schema(title = "예시 문장", nullable = true)
        private String example;

        public ProductSynopsis toEntity(UUID productId) {
            return ProductSynopsis.builder()
                .id(productId)
                .genre(genre)
                .length(length)
                .purpose(purpose)
                .logline(logline)
                .example(example)
                .build();
        }
    }

    @Getter
    @Setter
    public static class Worldview {

        @Schema(title = "지리", nullable = true)
        private String geography;
        @Schema(title = "역사", nullable = true)
        private String history;
        @Schema(title = "정치", nullable = true)
        private String politics;
        @Schema(title = "사회", nullable = true)
        private String society;
        @Schema(title = "종교", nullable = true)
        private String religion;
        @Schema(title = "경제", nullable = true)
        private String economy;
        @Schema(title = "기술", nullable = true)
        private String technology;
        @Schema(title = "생활", nullable = true)
        private String lifestyle;
        @Schema(title = "언어", nullable = true)
        private String language;
        @Schema(title = "문화", nullable = true)
        private String culture;
        @Schema(title = "종족", nullable = true)
        private String species;
        @Schema(title = "직업", nullable = true)
        private String occupation;
        @Schema(title = "갈등 관계", nullable = true)
        private String conflict;
        @Schema(title = "커스텀 필드", nullable = true)
        private List<CustomField> customFields;

        public ProductWorldview toEntity(UUID productId) {
            return ProductWorldview.builder()
                .id(productId)
                .geography(geography)
                .history(history)
                .politics(politics)
                .society(society)
                .religion(religion)
                .economy(economy)
                .technology(technology)
                .lifestyle(lifestyle)
                .language(language)
                .culture(culture)
                .species(species)
                .occupation(occupation)
                .conflict(conflict)
                .build();
        }
    }
}
