package com.challenge.ventas.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ventas API")
                        .version("3.1.0")
                        .description("API documentation for my application")
                        .contact(new Contact().name("Felix Alaimo").email("felix.alaimo@gmail.com"))
                );
    }
    
    @Bean
    GroupedOpenApi sellingPointApi() {
    	return GroupedOpenApi.builder()
    			.group("1 - Selling Point")
    			.pathsToMatch("/selling-point/**")
    			.build();
    }
    
    @Bean
    GroupedOpenApi costsApi() {
    	return GroupedOpenApi.builder()
    			.group("2 - Costs")
    			.pathsToMatch("/costs/**")
    			.build();
    }
    
    @Bean
    GroupedOpenApi accreditationsApi() {
        return GroupedOpenApi.builder()
                .group("3 - Accreditations")
                .pathsToMatch("/sale-accreditation/**")
                .build();
    }
}
