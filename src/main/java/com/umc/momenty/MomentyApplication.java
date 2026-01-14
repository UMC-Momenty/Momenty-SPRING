package com.umc.momenty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MomentyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomentyApplication.class, args);
	}

}
