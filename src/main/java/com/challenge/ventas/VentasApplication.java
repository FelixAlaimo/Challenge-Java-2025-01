package com.challenge.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.challenge.ventas.*")
@EntityScan(basePackages = "com.challenge.ventas.persistence.model")
public class VentasApplication {

	public static void main(String[] args) {
		initializeApplication(args);
	}
	
	static ConfigurableApplicationContext initializeApplication(String[] args) {
		return SpringApplication.run(VentasApplication.class, args);
	}
}
