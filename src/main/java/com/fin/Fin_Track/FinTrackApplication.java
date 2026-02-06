package com.fin.Fin_Track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.fin")
@EntityScan("com.fin.model")
@EnableAutoConfiguration
@EnableJpaRepositories("com.fin.repository")
public class FinTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinTrackApplication.class, args);
	}

}
