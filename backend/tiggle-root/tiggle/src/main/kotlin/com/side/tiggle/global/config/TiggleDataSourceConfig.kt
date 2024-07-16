package com.side.tiggle.global.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.side.tiggle.domain.**.repository"],
    entityManagerFactoryRef = "tiggleEntityManagerFactory",
)
class TiggleDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-tiggle")
    fun tiggleDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    @Primary
    fun tiggleEntityManagerFactory(
        @Qualifier("tiggleDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource)
            setPackagesToScan("com.side.tiggle")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            persistenceUnitName = "tiggle"
        }

    @Bean
    @Primary
    fun tiggleTransactionManager(
        @Qualifier("tiggleEntityManagerFactory") entityManagerFactory: LocalContainerEntityManagerFactoryBean
    ): JpaTransactionManager = JpaTransactionManager(entityManagerFactory.`object`!!)
}