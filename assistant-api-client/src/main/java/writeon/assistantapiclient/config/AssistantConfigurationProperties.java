package writeon.assistantapiclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "assistant.server")
public class AssistantConfigurationProperties {
    private String ip;
    private int port;
    private int connectionTimeoutMs;
    private int readTimeoutMs;
}
