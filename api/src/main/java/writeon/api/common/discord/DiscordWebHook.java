package writeon.api.common.discord;

import writeon.api.common.discord.model.EmbedObject;
import writeon.api.common.discord.model.Field;
import writeon.api.common.discord.model.JsonObject;
import writeon.api.common.enums.exception.InternalServerException;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.LogUtil;
import org.springframework.web.reactive.function.client.WebClient;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DiscordWebHook {

    private final String urlString;
    private final List<EmbedObject> embeds = new ArrayList<>();
    private String username;
    private String avatarUrl;
    private boolean tts;

    public DiscordWebHook(String webhookUrl) {
        this.urlString = webhookUrl;
    }

    public void addEmbed(EmbedObject embed) {
        this.embeds.add(embed);
    }

    /**
     * Discrod LogBack 메세지 전송
     */
    public void execute() {
        if (this.embeds.isEmpty()) {
            throw new BaseException(InternalServerException.DISCORD_APPENDER_FAIL);
        }

        try {
            WebClient.create(urlString)
                .post()
                .header("Content-Type", "application/json")
                .header("User-Agent", "Java-DiscordWebhook-BY-Gelox_")
                .bodyValue(createDiscordEmbedObject(this.embeds, initializerDiscordSendForJsonObject(new JsonObject())).toString().getBytes(
                    StandardCharsets.UTF_8))
                .retrieve()
                .toBodilessEntity()
                .subscribe((e) -> {
                }, (error) -> {
                    LogUtil.info("Discord Appender Fail: " + error.getLocalizedMessage());
                });
        } catch (Exception e) {
            LogUtil.info("Discord Appender Fail: " + e.getLocalizedMessage());
        }
    }

    private JsonObject initializerDiscordSendForJsonObject(JsonObject json) {
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);
        return json;
    }

    private JsonObject createDiscordEmbedObject(List<EmbedObject> embeds, JsonObject json) {
        if (embeds.isEmpty()) {
            throw new BaseException(InternalServerException.DISCORD_APPENDER_FAIL);
        }

        List<JsonObject> embedObjects = new ArrayList<>();

        for (EmbedObject embed : embeds) {
            JsonObject jsonEmbed = new JsonObject();

            jsonEmbed.put("title", embed.getTitle());
            jsonEmbed.put("description", embed.getDescription());
            jsonEmbed.put("url", embed.getUrl());

            setEmbedColor(embed, jsonEmbed);
            setEmbedMessageFields(embed.getFields(), jsonEmbed);

            embedObjects.add(jsonEmbed);
        }
        json.put("embeds", embedObjects.toArray());
        return json;
    }

    /**
     * Embed 메세지 테두리 색상 설정
     */
    private void setEmbedColor(EmbedObject embed, JsonObject jsonEmbed) {
        if (embed.getColor() != null) {
            Color color = embed.getColor();
            int rgb = color.getRed();
            rgb = (rgb << 8) + color.getGreen();
            rgb = (rgb << 8) + color.getBlue();

            jsonEmbed.put("color", rgb);
        }
    }

    /**
     * 텍스트 필드 목록 설정
     */
    private void setEmbedMessageFields(List<Field> fields, JsonObject jsonEmbed) {
        List<JsonObject> jsonFields = new ArrayList<>();

        for (Field field : fields) {
            JsonObject jsonField = new JsonObject();

            jsonField.put("name", field.getName());
            jsonField.put("value", field.getValue());
            jsonField.put("inline", field.isInline());

            jsonFields.add(jsonField);
        }

        jsonEmbed.put("fields", jsonFields.toArray());
    }
}
