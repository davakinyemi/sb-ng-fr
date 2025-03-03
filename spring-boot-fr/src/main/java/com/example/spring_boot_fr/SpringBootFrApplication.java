package com.example.spring_boot_fr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootFrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFrApplication.class, args);
	}

}
