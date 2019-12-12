package com.rohov.tagsoft.configuration.db;

import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.rohov.tagsoft",
        entityManagerFactoryRef = "multiEntityManager",
        transactionManagerRef = "multiTransactionManager")
public class PersistenceConfiguration {

    @Autowired
    Environment env;

    private final String PACKAGE_SCAN = "com.rohov.tagsoft";

    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().type(PGSimpleDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url(env.getProperty("application.datasource.default.jdbc-url"))
                .username(env.getProperty("application.datasource.default.username"))
                .password(env.getProperty("application.datasource.default.password"))
                .build();
    }

    @Bean(name = "usaDataSource")
    public DataSource usaDataSource() {
        return DataSourceBuilder.create().type(PGSimpleDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url(env.getProperty("application.datasource.usa.jdbc-url"))
                .username(env.getProperty("application.datasource.usa.username"))
                .password(env.getProperty("application.datasource.usa.password"))
                .build();
    }

    @Bean(name = "canadaDataSource")
    public DataSource canadaDataSource() {
        return DataSourceBuilder.create().type(PGSimpleDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url(env.getProperty("application.datasource.canada.jdbc-url"))
                .username(env.getProperty("application.datasource.canada.username"))
                .password(env.getProperty("application.datasource.canada.password"))
                .build();
    }

    @Bean(name = "multiRoutingDataSource")
    public DataSource multiRoutingDataSource() {
        Map<Object, Object> targetDataSource = new HashMap<>();

        targetDataSource.put(DBType.DEFAULT, defaultDataSource());
        targetDataSource.put(DBType.USA, usaDataSource());
        targetDataSource.put(DBType.CANADA, canadaDataSource());

        MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();

        multiRoutingDataSource.setDefaultTargetDataSource(defaultDataSource());
        multiRoutingDataSource.setTargetDataSources(targetDataSource);

        return multiRoutingDataSource;
    }

    @Bean(name = "multiEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(multiRoutingDataSource());
        em.setPackagesToScan(PACKAGE_SCAN);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean(name = "multiTransactionManager")
    public PlatformTransactionManager multiTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(multiEntityManager().getObject());

        return transactionManager;
    }

    @Primary
    @Bean(name = "dbSessionFactory")
    public LocalSessionFactoryBean dbSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setDataSource(multiRoutingDataSource());
        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.ddl-auto", "create");
        properties.put("hibernate.dialect", org.hibernate.dialect.PostgreSQLDialect.class);

        return properties;
    }

}
