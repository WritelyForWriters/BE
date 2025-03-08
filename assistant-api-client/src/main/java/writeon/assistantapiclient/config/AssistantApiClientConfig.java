package writeon.assistantapiclient.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import writeon.assistantapiclient.AssistantApiClient;

@Configuration
@EnableConfigurationProperties({AssistantConfigurationProperties.class})
public class AssistantApiClientConfig {

    @Bean
    public AssistantApiClient assistantApiClient(AssistantConfigurationProperties configurationProperties) {
        return new AssistantApiClient(
            "http://" + configurationProperties.getIp() + ":" + configurationProperties.getPort(),
            configurationProperties.getConnectionTimeoutMs(),
            configurationProperties.getReadTimeoutMs()
        );
    }
}
