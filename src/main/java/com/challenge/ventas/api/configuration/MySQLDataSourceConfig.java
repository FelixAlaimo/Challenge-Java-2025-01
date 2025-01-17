package com.challenge.ventas.api.configuration;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.challenge.ventas.repository.persistence",
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager"
)
public class MySQLDataSourceConfig {

    @Bean
    DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/mydb")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("password")
                .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(mysqlDataSource())
                .packages("com.challenge.ventas.model")
                .persistenceUnit("mysql")
                .build();
    }

    @Bean
    JpaTransactionManager mysqlTransactionManager() {
        return new JpaTransactionManager(mysqlEntityManagerFactory(null).getObject());
    }
}