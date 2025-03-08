package writeon.api.common.discord;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import writeon.api.common.discord.model.EmbedObject;
import writeon.api.common.enums.exception.InternalServerException;
import writeon.api.common.exception.BaseException;
import writeon.api.common.util.MDCUtil;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

@Setter
@Profile("!local")
public class DiscordLogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private String webhookUrl;

    @Override
    protected void append(ILoggingEvent event) {
        if (event.getLevel().isGreaterOrEqual(Level.ERROR)) {
            sendErrorLog(event);
        }
    }

    public void sendErrorLog(ILoggingEvent event) {
        DiscordWebHook discordWebhook = new DiscordWebHook(webhookUrl);
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();

        String requestUri = mdcPropertyMap.get(MDCUtil.REQUEST_URI_MDC);
        if (!Pattern.matches("/products/.*|/auth/.*|/health-check/.*", requestUri)) {
            return;
        }

        Color messageColor = Color.red;
        String message = event.getMessage();

        int newlineIndex = message.indexOf("\n");
        if (newlineIndex != -1) {
            message = message.substring(0, newlineIndex).trim();
        } else {
            message = "EXCEPTION 정보가 남지 않았습니다.";
        }

        discordWebhook.addEmbed(new EmbedObject()
            .setTitle("[에러 내용]")
            .setColor(messageColor)
            .setDescription(message)
            .addField(MDCUtil.REQUEST_URI_MDC, mdcPropertyMap.get(MDCUtil.REQUEST_METHOD_MDC) + " " + mdcPropertyMap.get(MDCUtil.REQUEST_URI_MDC), true)
            .addField("[오류 발생 시간]", LocalDateTime.now().toString(), true)
            .addField(MDCUtil.REQUEST_IP_MDC, mdcPropertyMap.get(MDCUtil.REQUEST_IP_MDC), false)
            .addField(MDCUtil.HEADER_MAP_MDC,
                escapeJsonInternal(mdcPropertyMap.get(MDCUtil.HEADER_MAP_MDC)),
                false)
            .addField(MDCUtil.PARAMETER_MAP_MDC,
                escapeJsonInternal(mdcPropertyMap.get(MDCUtil.PARAMETER_MAP_MDC)),
                false));

        discordWebhook.addEmbed(new EmbedObject()
            .setTitle("[Exception 상세 내용]")
            .setColor(messageColor)
            .setDescription(escapeJsonInternal(event.getMessage().substring(0, 2000)))
        );

        try {
            discordWebhook.execute();
        } catch (Exception e) {
            throw new BaseException(InternalServerException.DISCORD_APPENDER_FAIL);
        }
    }

    private String escapeJsonInternal(final String input) {
        // 입력이 null이거나 빈 문자열인 경우 그대로 반환
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            final char ch = input.charAt(i);
            switch (ch) {
                case '"':
                    builder.append("\\\"");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                default:
                    if (ch < ' ' || (ch >= '\u0080' && ch < '\u00a0')
                        || (ch >= '\u2000' && ch < '\u2100')) {
                        builder.append("\\u").append(Integer.toHexString(ch | 0x10000).substring(1));
                    } else {
                        builder.append(ch);
                    }
            }
        }
        return builder.toString();
    }
}
