package com.side.tiggle.global.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.*
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    @Qualifier("batchDataSource") private val batchDataSource: DataSource,
    @Qualifier("batchTransactionManager") private val batchTransactionManager: JpaTransactionManager
) : BatchConfigurer {

    private var jobRepository: JobRepository? = null
    private var jobExplorer: JobExplorer? = null

    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("exampleJob")
            .incrementer(RunIdIncrementer())
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet(tasklet())
            .build()
    }

    @Bean
    fun tasklet(): Tasklet {
        return Tasklet { contribution, chunkContext ->
            println("Hello, Spring Batch with Kotlin!")
            RepeatStatus.FINISHED
        }
    }

    override fun getJobRepository(): JobRepository {
        val factory = JobRepositoryFactoryBean()
        factory.setDataSource(batchDataSource)
        factory.setTransactionManager(batchTransactionManager)
        factory.afterPropertiesSet()
        return factory.getObject()!!
    }

    override fun getTransactionManager(): PlatformTransactionManager {
        return ResourcelessTransactionManager()
    }

    override fun getJobLauncher(): JobLauncher {
        val jobLauncher = SimpleJobLauncher()
        jobLauncher.setJobRepository(getJobRepository())
        jobLauncher.afterPropertiesSet()
        return jobLauncher
    }

    override fun getJobExplorer(): JobExplorer {
        if (jobExplorer == null) {
            val factory = JobExplorerFactoryBean()
            factory.setDataSource(batchDataSource)
            factory.afterPropertiesSet()
            jobExplorer = factory.getObject()
        }
        return jobExplorer!!
    }
}
