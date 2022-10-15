package com.unicamp.inf332.cansei.crosscutting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.unicamp.inf332.cansei.application.services.DBService;
import com.unicamp.inf332.cansei.application.services.IEmailService;
import com.unicamp.inf332.cansei.application.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() {
		try {
			dbService.addEstadoTeste();
			dbService.instantiateTestDatabase();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Bean
	public IEmailService emailService() {
		return new MockEmailService();
	}
}
