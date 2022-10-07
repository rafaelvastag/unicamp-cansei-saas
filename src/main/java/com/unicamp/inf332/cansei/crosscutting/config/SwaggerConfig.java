package com.unicamp.inf332.cansei.crosscutting.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.unicamp.inf332.cansei"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(securitySchemes());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Cansei SaaS API")
				.description("Api do projeto de brecho Cansei.")
				.version("1.0")
				.contact(contact())
				.build();
	}

	private Contact contact() {
		return new Contact("Equipe 07", "", "casei@cansei-support.com");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build();
	}

	private List<SecurityScheme> securitySchemes() {
		return Arrays.asList(new ApiKey("JWT", "Authorization", "header"));
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

		AuthorizationScope[] scopes = new AuthorizationScope[1];
		scopes[0] = authorizationScope;

		List<SecurityReference> auths = new ArrayList<>();
		auths.add(new SecurityReference("JWT", scopes));
		return auths;
	}
}
