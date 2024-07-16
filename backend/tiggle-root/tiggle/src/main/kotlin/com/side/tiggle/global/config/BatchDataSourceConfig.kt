package com.side.tiggle.global.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.side.tiggle.batch"],
    entityManagerFactoryRef = "batchEntityManagerFactory",
    transactionManagerRef = "batchTransactionManager"
)
class BatchDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-batch")
    fun batchDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun batchEntityManagerFactory(
        @Qualifier("batchDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource)
            setPackagesToScan("com.side.tiggle.batch")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            persistenceUnitName = "batch"
        }

    @Bean
    fun batchTransactionManager(
        @Qualifier("batchEntityManagerFactory") entityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): JpaTransactionManager = JpaTransactionManager(entityManagerFactory.`object`!!)
}