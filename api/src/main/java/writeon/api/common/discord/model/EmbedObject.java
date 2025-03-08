package writeon.api.common.discord.model;

import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class EmbedObject {

    private final List<Field> fields = new ArrayList<>();
    private String title;
    private String description;
    private String url;
    private Color color;

    public EmbedObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedObject setUrl(String url) {
        this.url = url;
        return this;
    }

    public EmbedObject setColor(Color color) {
        this.color = color;
        return this;
    }

    public EmbedObject addField(String name, String value, boolean inline) {
        this.fields.add(new Field(name, value, inline));
        return this;
    }
}
