package writeon.api.common.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.amplitude.Amplitude;


@Configuration
public class AmplitudeConfig {
    @Value("${service.tracking.amplitude.api-key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Amplitude amplitude = Amplitude.getInstance();
        amplitude.init(apiKey);
    }

}
