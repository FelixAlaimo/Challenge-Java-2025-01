package com.challenge.ventas.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactoryH2",
		transactionManagerRef = "transactionManagerH2",
        basePackages = {"com.challenge.ventas.cache.repository"}
)
public class H2RepositoryConfig {
	
	@Primary
	@Bean(name = "dataSourceH2")
	@ConfigurationProperties(prefix = "spring.h2.datasource")
    DataSource dataSourceH2() {
        return DataSourceBuilder.create().build();
    }
	
	@Primary
	@Bean(name = "entityManagerFactoryH2")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryH2(EntityManagerFactoryBuilder builder,
            @Qualifier("dataSourceH2") DataSource dataSource) {
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.challenge.ventas.cache.model")
                .persistenceUnit("h2")
                .build();
    }
	
	@Bean
    @Primary
    PlatformTransactionManager transactionManagerH2(@Qualifier("entityManagerFactoryH2") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
