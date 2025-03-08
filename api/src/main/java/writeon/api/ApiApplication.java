package writeon.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"writeon.api", "writeon.assistantapiclient"})
@EntityScan(basePackages = "writeon.domain")
@EnableJpaRepositories(
	basePackages = "writeon.domain",
	includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*JpaRepository")
)
@EnableRedisRepositories(
	basePackages = "writeon.domain",
	includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*RedisRepository")
)
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
