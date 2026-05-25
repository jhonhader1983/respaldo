package com.lamaison.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LamaisonAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(LamaisonAuthApplication.class, args);
	}

}
