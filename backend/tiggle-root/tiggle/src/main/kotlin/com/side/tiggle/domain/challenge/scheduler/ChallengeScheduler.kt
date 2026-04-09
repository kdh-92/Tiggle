package com.side.tiggle.domain.challenge.scheduler

import com.side.tiggle.domain.challenge.service.ChallengeService
import com.side.tiggle.global.common.logging.log
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ChallengeScheduler(
    private val challengeService: ChallengeService
) {

    /**
     * 매일 새벽 0시 30분에 챌린지 일일 처리 실행
     */
    @Scheduled(cron = "0 30 0 * * *")
    fun processDailyChallenges() {
        log.info("챌린지 일일 처리 시작")
        try {
            challengeService.processDaily()
            log.info("챌린지 일일 처리 완료")
        } catch (e: Exception) {
            log.error("챌린지 일일 처리 중 오류 발생", e)
        }
    }
}
