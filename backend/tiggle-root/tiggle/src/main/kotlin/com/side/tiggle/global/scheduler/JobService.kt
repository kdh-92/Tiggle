package com.side.tiggle.global.scheduler

import org.springframework.stereotype.Service

@Service
class JobService {
    fun runWeeklyJob() {
        // 주간 작업 로직
        println("Weekly job is running...")
    }

    fun runMonthlyJob() {
        // 월간 작업 로직
        println("Monthly job is running...")
    }

    fun runNowJob() {
        // 현재 작업 로직
        println("Now job is running...")
    }
}
