package com.writely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(
		includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*JpaRepository")
)
@EnableRedisRepositories(
		includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*RedisRepository")
)
public class WritelyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WritelyApplication.class, args);
	}

}
