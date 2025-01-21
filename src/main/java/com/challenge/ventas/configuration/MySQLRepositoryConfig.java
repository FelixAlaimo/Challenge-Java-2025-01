package com.challenge.ventas.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.challenge.ventas.repository.persistence",
        entityManagerFactoryRef = "entityManagerFactoryMySQL",
        transactionManagerRef = "transactionManagerMySQL"
)
public class MySQLRepositoryConfig {

}
