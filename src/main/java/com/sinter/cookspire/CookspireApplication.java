package com.sinter.cookspire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition
@SecurityScheme(type=SecuritySchemeType.HTTP,
name="bearerAuth",
scheme = "bearer")
public class CookspireApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookspireApplication.class, args);
	}

}
