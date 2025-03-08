package writeon.api.product.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.domain.product.*;

import java.util.List;
import java.util.UUID;

@Getter
public class ProductTemplateResponse {

    private final UUID id;
    private final List<Character> characters;
    private final IdeaNote ideaNote;
    private final Plot plot;
    private final Synopsis synopsis;
    private final Worldview worldview;

    public ProductTemplateResponse(Product product) {
        this.id = product.getId();
        this.characters = product.getCharacters().stream()
            .map(Character::new)
            .toList();
        this.ideaNote = product.getIdeaNote() != null ? new IdeaNote(product.getIdeaNote()) : null;
        this.plot = product.getPlot() != null ? new Plot(product.getPlot()) : null;
        this.synopsis = product.getSynopsis() != null ? new Synopsis(product.getSynopsis()) : null;
        this.worldview = product.getWorldview() != null ? new Worldview(product.getWorldview()) : null;
    }

    @Getter
    @Setter
    public static class Character {

        private final UUID id;
        @Schema(title = "소개", nullable = true)
        private final String intro;
        @Schema(name = "이름", nullable = true)
        private final String name;
        @Schema(name = "나이", nullable = true)
        private final Short age;
        @Schema(name = "성별", nullable = true)
        private final String gender;
        @Schema(name = "직업", nullable = true)
        private final String occupation;
        @Schema(name = "외모", nullable = true)
        private final String appearance;
        @Schema(name = "성격", nullable = true)
        private final String personality;
        @Schema(name = "특징", nullable = true)
        private final String characteristic;
        @Schema(name = "주요관계", nullable = true)
        private final String relationship;
        @Schema(name = "커스텀 필드 목록", nullable = true)
        private final List<CustomField> customFields;

        public Character(ProductCharacter character) {
            this.id = character.getId();
            this.intro = character.getIntro();
            this.name = character.getName();
            this.age = character.getAge();
            this.gender = character.getGender();
            this.occupation = character.getOccupation();
            this.appearance = character.getAppearance();
            this.personality = character.getPersonality();
            this.characteristic = character.getCharacteristic();
            this.relationship = character.getRelationship();
            this.customFields = character.getCustomFields().stream()
                .map(CustomField::new)
                .toList();
        }
    }

    @Getter
    @Setter
    public static class CustomField {

        private final UUID id;
        @Schema(title = "필드 이름")
        private final String name;
        @Schema(title = "필드 내용")
        private final String content;
        @Schema(title = "필드 순서")
        private final Short seq;

        public CustomField(ProductCustomField customField) {
            this.id = customField.getId();
            this.name = customField.getName();
            this.content = customField.getContent();
            this.seq = customField.getSeq();
        }
    }

    @Getter
    @Setter
    public static class IdeaNote {

        private final UUID id;
        @Schema(title = "제목", nullable = true)
        private final String title;
        @Schema(title = "내용", nullable = true)
        private final String content;

        public IdeaNote(ProductIdeaNote ideaNote) {
            this.id = ideaNote.getId();
            this.title = ideaNote.getTitle();
            this.content = ideaNote.getContent();
        }
    }

    @Getter
    @Setter
    public static class Plot {

        private final UUID id;
        @Schema(title = "내용", nullable = true)
        private final String content;

        public Plot(ProductPlot plot) {
            this.id = plot.getId();
            this.content = plot.getContent();
        }
    }

    @Getter
    @Setter
    public static class Synopsis {

        private final UUID id;
        @Schema(title = "장르")
        private final String genre;
        @Schema(title = "분량", nullable = true)
        private final String length;
        @Schema(title = "기획 의도", nullable = true)
        private final String purpose;
        @Schema(title = "로그라인", nullable = true)
        private final String logline;
        @Schema(title = "예시 문장", nullable = true)
        private final String example;

        public Synopsis(ProductSynopsis synopsis) {
            this.id = synopsis.getId();
            this.genre = synopsis.getGenre();
            this.length = synopsis.getLength();
            this.purpose = synopsis.getPurpose();
            this.logline = synopsis.getLogline();
            this.example = synopsis.getExample();
        }
    }

    @Getter
    @Setter
    public static class Worldview {

        private final UUID id;
        @Schema(title = "지리", nullable = true)
        private final String geography;
        @Schema(title = "역사", nullable = true)
        private final String history;
        @Schema(title = "정치", nullable = true)
        private final String politics;
        @Schema(title = "사회", nullable = true)
        private final String society;
        @Schema(title = "종교", nullable = true)
        private final String religion;
        @Schema(title = "경제", nullable = true)
        private final String economy;
        @Schema(title = "기술", nullable = true)
        private final String technology;
        @Schema(title = "생활", nullable = true)
        private final String lifestyle;
        @Schema(title = "언어", nullable = true)
        private final String language;
        @Schema(title = "문화", nullable = true)
        private final String culture;
        @Schema(title = "종족", nullable = true)
        private final String species;
        @Schema(title = "직업", nullable = true)
        private final String occupation;
        @Schema(title = "갈등 관계", nullable = true)
        private final String conflict;
        @Schema(name = "커스텀 필드 목록", nullable = true)
        private final List<CustomField> customFields;

        public Worldview(ProductWorldview worldview) {
            this.id = worldview.getId();
            this.geography = worldview.getGeography();
            this.history = worldview.getHistory();
            this.politics = worldview.getPolitics();
            this.society = worldview.getSociety();
            this.religion = worldview.getReligion();
            this.economy = worldview.getEconomy();
            this.technology = worldview.getTechnology();
            this.lifestyle = worldview.getLifestyle();
            this.language = worldview.getLanguage();
            this.culture = worldview.getCulture();
            this.species = worldview.getSpecies();
            this.occupation = worldview.getOccupation();
            this.conflict = worldview.getConflict();
            this.customFields = worldview.getCustomFields().stream()
                .map(CustomField::new)
                .toList();
        }
    }
}
