package com.unicamp.inf332.cansei.crosscutting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.unicamp.inf332.cansei.application.services.DBService;
import com.unicamp.inf332.cansei.application.services.IEmailService;
import com.unicamp.inf332.cansei.application.services.MockEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws Exception {

		if (!"create".equals(strategy)) {
			return false;
		}

		dbService.instantiateTestDatabase();
		return true;
	}

	@Bean
	public IEmailService emailService() {
		return new MockEmailService();
	}

}
