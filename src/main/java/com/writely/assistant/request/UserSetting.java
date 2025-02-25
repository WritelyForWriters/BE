package com.writely.assistant.request;

import com.writely.product.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UserSetting {

    private final Synopsis synopsis;
    private final Worldview worldview;
    private final List<Character> character;
    private final Plot plot;
    private final IdeaNote ideaNote;
    private final List<CustomField> customField;

    public UserSetting(Product product) {
        this.character = product.getCharacters().stream()
            .map(UserSetting.Character::new)
            .toList();
        this.customField = product.getCustomFields().stream()
            .map(UserSetting.CustomField::new)
            .toList();
        this.ideaNote = product.getIdeaNote() != null ? new UserSetting.IdeaNote(product.getIdeaNote()) : null;
        this.plot = product.getPlot() != null ? new UserSetting.Plot(product.getPlot()) : null;
        this.synopsis = product.getSynopsis() != null ? new UserSetting.Synopsis(product.getSynopsis()) : null;
        this.worldview = product.getWorldview() != null ? new UserSetting.Worldview(product.getWorldview()) : null;
    }

    @Getter
    public static class Character {

        @Schema(title = "소개", nullable = true)
        private final String intro;
        @Schema(name = "이름", nullable = true)
        private final String character_name;
        @Schema(name = "나이", nullable = true)
        private final String age;
        @Schema(name = "성별", nullable = true)
        private final String gender;
        @Schema(name = "직업", nullable = true)
        private final String character_occupation;
        @Schema(name = "외모", nullable = true)
        private final String appearance;
        @Schema(name = "성격", nullable = true)
        private final String personality;
        @Schema(name = "특징", nullable = true)
        private final String characteristic;
        @Schema(name = "주요관계", nullable = true)
        private final String relationship;

        public Character(ProductCharacter character) {
            this.intro = character.getIntro();
            this.character_name = character.getName();
            this.age = String.valueOf(character.getAge());
            this.gender = character.getGender();
            this.character_occupation = character.getOccupation();
            this.appearance = character.getAppearance();
            this.personality = character.getPersonality();
            this.characteristic = character.getCharacteristic();
            this.relationship = character.getRelationship();
        }
    }

    @Getter
    public static class CustomField {

        @Schema(title = "섹션 코드")
        private final String section_code;
        @Schema(title = "필드 이름")
        private final String custom_field_name;
        @Schema(title = "필드 내용")
        private final String custom_field_content;

        public CustomField(ProductCustomField customField) {
            this.section_code = customField.getSectionType().getCode();
            this.custom_field_name = customField.getName();
            this.custom_field_content = customField.getContent();
        }
    }

    @Getter
    public static class IdeaNote {

        @Schema(title = "제목", nullable = true)
        private final String idea_title;
        @Schema(title = "내용", nullable = true)
        private final String idea_content;

        public IdeaNote(ProductIdeaNote ideaNote) {
            this.idea_title = ideaNote.getTitle();
            this.idea_content = ideaNote.getContent();
        }
    }

    @Getter
    public static class Plot {

        @Schema(title = "발단", nullable = true)
        private final String exposition;
        @Schema(title = "전개", nullable = true)
        private final String complication;
        @Schema(title = "위기", nullable = true)
        private final String climax;
        @Schema(title = "결말", nullable = true)
        private final String resolution;

        public Plot(ProductPlot plot) {
            this.exposition = plot.getExposition();
            this.complication = plot.getComplication();
            this.climax = plot.getClimax();
            this.resolution = plot.getResolution();
        }
    }

    @Getter
    public static class Synopsis {

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
            this.genre = synopsis.getGenre();
            this.length = synopsis.getLength();
            this.purpose = synopsis.getPurpose();
            this.logline = synopsis.getLogline();
            this.example = synopsis.getExample();
        }
    }

    @Getter
    public static class Worldview {

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
        private final String custom_field;

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
            this.custom_field = "";
        }
    }
}
