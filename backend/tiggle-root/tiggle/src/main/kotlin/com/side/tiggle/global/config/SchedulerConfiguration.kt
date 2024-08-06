package com.side.tiggle.global.config

import com.side.tiggle.domain.scheduler.service.JobService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
@EnableScheduling
class SchedulerConfig(
    private val jobService: JobService,
    @Value("\${week-schedule.cron}") private val weekCron: String,
    @Value("\${week-schedule.use}") private val useWeekSchedule: Boolean,
    @Value("\${monthly-schedule.cron}") private val monthlyCron: String,
    @Value("\${monthly-schedule.use}") private val useMonthlySchedule: Boolean,
    @Value("\${now-schedule.cron}") private val nowCron: String,
    @Value("\${now-schedule.use}") private val useNowSchedule: Boolean
) : SchedulingConfigurer {

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        if (useWeekSchedule) {
            taskRegistrar.addCronTask({ runJob("weekly") }, weekCron)
        }
        if (useMonthlySchedule) {
            taskRegistrar.addCronTask({ runJob("monthly") }, monthlyCron)
        }
        if (useNowSchedule) {
            taskRegistrar.addCronTask({ runJob("now") }, nowCron)
        }
    }

    private fun runJob(jobType: String) {
        try {
            when (jobType) {
                "weekly" -> jobService.generateWeeklySummary()
                "monthly" -> jobService.generateMonthlySummary()
                "now" -> jobService.runNowJob()
                else -> println("Unknown job type: $jobType")
            }
        } catch (e: InterruptedException) {
            println("* Thread가 강제 종료되었습니다. Message: ${e.message}")
        } catch (e: Exception) {
            println("* Batch 시스템이 예기치 않게 종료되었습니다. Message: ${e.message}")
        }
    }
}
