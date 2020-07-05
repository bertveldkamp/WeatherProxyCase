package com.capgemini.logius.weatherproxy;

import com.capgemini.logius.weatherproxy.client.WeatherClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherproxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherproxyApplication.class, args);
	}

	@Bean
	public WeatherClient weatherClient() {
		return new WeatherClient();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
