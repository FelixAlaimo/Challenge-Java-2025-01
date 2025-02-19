package com.challenge.ventas.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactoryMySQL",
		transactionManagerRef = "transactionManagerMySQL",
        basePackages = {"com.challenge.ventas.persistence.repository"}
)
public class MySQLRepositoryConfig {
	
	@Bean(name = "dataSourceMySQL")
	@ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSourceMySQL() {
        return DataSourceBuilder.create().build();
    }

	@Bean(name = "entityManagerFactoryMySQL")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryMySQL(EntityManagerFactoryBuilder builder,
            @Qualifier("dataSourceMySQL") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.challenge.ventas.persistence.model")
                .persistenceUnit("mySQL")
                .build();
    }

    @Bean
    PlatformTransactionManager transactionManagerMySQL(@Qualifier("entityManagerFactoryMySQL") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
