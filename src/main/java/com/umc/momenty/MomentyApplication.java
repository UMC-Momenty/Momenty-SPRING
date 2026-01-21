package com.umc.momenty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@EnableResilientMethods
@SpringBootApplication
public class MomentyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentyApplication.class, args);
	}

}
