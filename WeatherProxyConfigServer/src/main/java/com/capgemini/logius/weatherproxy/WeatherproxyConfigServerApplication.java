package com.capgemini.logius.weatherproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class WeatherproxyConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherproxyConfigServerApplication.class, args);
	}

}
