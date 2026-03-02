package com.petroskyragiannis.tainia_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TainiaBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TainiaBotApplication.class, args);
	}

}
