package com.challenge.ventas.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

	@Bean(name = "dataSourceH2")
    @Primary
    DataSource dataSourceH2() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("password")
                .build();
    }

	@Bean(name = "dataSourceMySQL")
    DataSource dataSourceMySQL() {
		String dbUrl = System.getProperty("env") != null && System.getProperty("env").equals("docker")
	            ? "jdbc:mysql://mysql-db:3306/ventasDB"
	            : "jdbc:mysql://localhost:3306/ventasDB";
        return DataSourceBuilder.create()
                .url(dbUrl)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("rootpassword")
                .build();
    }

	@Bean(name = "entityManagerFactoryH2")
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactoryH2(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSourceH2") DataSource dataSource) {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");

        LocalContainerEntityManagerFactoryBean factoryBean = builder
                .dataSource(dataSource)
                .packages("com.challenge.ventas.model")
                .persistenceUnit("h2")
                .build();

        factoryBean.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create-drop");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        return factoryBean;
    }

	@Bean(name = "entityManagerFactoryMySQL")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryMySQL(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSourceMySQL") DataSource dataSource) {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");

        LocalContainerEntityManagerFactoryBean factoryBean = builder
                .dataSource(dataSource)
                .packages("com.challenge.ventas.model")
                .persistenceUnit("mysql")
                .build();

        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        return factoryBean;
    }

    @Bean
    @Primary
    PlatformTransactionManager transactionManagerH2(
            @Qualifier("entityManagerFactoryH2") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    PlatformTransactionManager transactionManagerMySQL(
            @Qualifier("entityManagerFactoryMySQL") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
}