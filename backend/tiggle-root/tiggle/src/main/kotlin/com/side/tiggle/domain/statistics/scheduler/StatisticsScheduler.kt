package com.side.tiggle.domain.statistics.scheduler

import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.statistics.service.StatisticsService
import com.side.tiggle.global.common.logging.log
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate

@Component
class StatisticsScheduler(
    private val statisticsService: StatisticsService,
    private val memberRepository: MemberRepository
) {

    /**
     * 매주 월요일 새벽 1시에 지난 주 통계 스냅샷 생성
     */
    @Scheduled(cron = "0 0 1 * * MON")
    fun generateWeeklySnapshots() {
        val lastMonday = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY)
        val members = memberRepository.findAll()

        log.info("주간 통계 스냅샷 생성 시작 - 대상 주: $lastMonday, 회원 수: ${members.size}")

        var successCount = 0
        var failCount = 0

        members.forEach { member ->
            try {
                statisticsService.generateWeeklySnapshot(member.id, lastMonday)
                successCount++
            } catch (e: Exception) {
                failCount++
                log.error("주간 통계 스냅샷 생성 실패 - memberId: ${member.id}", e)
            }
        }

        log.info("주간 통계 스냅샷 생성 완료 - 성공: $successCount, 실패: $failCount")
    }
}
