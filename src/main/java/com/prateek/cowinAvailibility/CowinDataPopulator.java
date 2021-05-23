package com.prateek.cowinAvailibility;

import com.prateek.cowinAvailibility.configuration.ConfigValueProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties(value = ConfigValueProperties.class)
public class CowinDataPopulator {

	public static void main(String[] args) {
		SpringApplication.run(CowinDataPopulator.class, args);
	}

}
