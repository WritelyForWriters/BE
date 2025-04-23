package writeon.assistantapiclient.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import writeon.domain.product.*;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSetting {

    private final Synopsis synopsis;
    private final Worldview worldview;
    private final List<Character> characters;
    private final Plot plot;
    private final IdeaNote ideaNote;

    public UserSetting(Product product) {
        this.characters = product.getCharacters().stream()
            .map(Character::new)
            .toList();
        this.ideaNote = product.getIdeaNote() != null ? new IdeaNote(product.getIdeaNote()) : null;
        this.plot = product.getPlot() != null ? new Plot(product.getPlot()) : null;
        this.synopsis = product.getSynopsis() != null ? new Synopsis(product.getSynopsis()) : null;
        this.worldview = product.getWorldview() != null ? new Worldview(product.getWorldview()) : null;
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Character {

        private final String intro;
        private final String character_name;
        private final String age;
        private final String gender;
        private final String character_occupation;
        private final String appearance;
        private final String personality;
        private final String characteristic;
        private final String relationship;
        private final List<CustomField> custom_fields;

        public Character(ProductCharacter character) {
            this.intro = character.getIntro();
            this.character_name = character.getName();
            this.age = String.valueOf(character.getAge());
            this.gender = character.getGender();
            this.character_occupation = character.getOccupation();
            this.appearance = character.getAppearance();
            this.personality = character.getPersonality();
            this.characteristic = "";
            this.relationship = character.getRelationship();
            this.custom_fields = character.getCustomFields().stream()
                .map(CustomField::new)
                .toList();
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CustomField {

        private final String custom_field_name;
        private final String custom_field_content;

        public CustomField(ProductCustomField customField) {
            this.custom_field_name = customField.getName();
            this.custom_field_content = customField.getContent();
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class IdeaNote {

        private final String idea_title;
        private final String idea_content;

        public IdeaNote(ProductIdeaNote ideaNote) {
            this.idea_title = ideaNote.getTitle();
            this.idea_content = ideaNote.getContent();
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Plot {

        private final String content;

        public Plot(ProductPlot plot) {
            this.content = plot.getContent();
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Synopsis {

        private final String genre;
        private final String length;
        private final String purpose;
        private final String logline;
        private final String example;

        public Synopsis(ProductSynopsis synopsis) {
            this.genre = synopsis.getGenre();
            this.length = synopsis.getLength();
            this.purpose = synopsis.getPurpose();
            this.logline = synopsis.getLogline();
            this.example = synopsis.getExample();
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Worldview {

        private final String geography;
        private final String history;
        private final String politics;
        private final String society;
        private final String religion;
        private final String economy;
        private final String technology;
        private final String lifestyle;
        private final String language;
        private final String culture;
        private final String species;
        private final String occupation;
        private final String conflict;
        private final List<CustomField> custom_fields;

        public Worldview(ProductWorldview worldview) {
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
            this.custom_fields = worldview.getCustomFields().stream()
                .map(CustomField::new)
                .toList();
        }
    }
}
