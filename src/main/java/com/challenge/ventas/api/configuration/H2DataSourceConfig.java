package com.challenge.ventas.api.configuration;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.challenge.ventas.repository.cache",
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager"
)
public class H2DataSourceConfig {

    @Bean
    @Primary
    DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("password")
                .build();
    }

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(h2DataSource())
                .packages("com.challenge.ventas.model")
                .persistenceUnit("h2")
                .build();
    }

    @Bean
    @Primary
    JpaTransactionManager h2TransactionManager() {
        return new JpaTransactionManager(h2EntityManagerFactory(null).getObject());
    }
}

