package com.yff.core.config.datasource;

import com.yff.core.jparepository.repository.BaseRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import javax.persistence.EntityManager;
import java.util.*;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryFactoryBeanClass=BaseRepositoryFactoryBean.class,
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {"com.yff.ecbackend"}) //设置Repository所在位置
public class PrimaryConfig {


    @Autowired
    private HibernateProperties hibernateProperties;


    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;


    @Autowired(required=false)
    private JpaProperties jpaProperties;


    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource)
                //设置实体类所在位置
                .packages("com.yff.ecbackend")
                .persistenceUnit("primaryPersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }


    private Map<String, Object> getVendorProperties() {
        /**
         * springboot1.5.x版本的方式jpaProperties.getHibernateProperties(new DataSource());
         * springboot2.0.x版本的阿方式jpaProperties.getHibernateProperties(new HibernateSettings());
         */
//        return jpaProperties.getHibernateProperties(new HibernateSettings());

        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }


    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }


}
