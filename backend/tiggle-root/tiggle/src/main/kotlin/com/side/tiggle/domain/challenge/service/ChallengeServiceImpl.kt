package com.side.tiggle.domain.challenge.service

import com.side.tiggle.domain.challenge.dto.req.ChallengeCreateReqDto
import com.side.tiggle.domain.challenge.dto.resp.ChallengeDetailRespDto
import com.side.tiggle.domain.challenge.dto.resp.ChallengeRespDto
import com.side.tiggle.domain.challenge.dto.resp.DailyLogRespDto
import com.side.tiggle.domain.challenge.exception.ChallengeException
import com.side.tiggle.domain.challenge.exception.error.ChallengeErrorCode
import com.side.tiggle.domain.challenge.model.Challenge
import com.side.tiggle.domain.challenge.model.ChallengeDailyLog
import com.side.tiggle.domain.challenge.model.ChallengeStatus
import com.side.tiggle.domain.challenge.repository.ChallengeDailyLogRepository
import com.side.tiggle.domain.challenge.repository.ChallengeRepository
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.global.common.logging.log
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ChallengeServiceImpl(
    private val challengeRepository: ChallengeRepository,
    private val challengeDailyLogRepository: ChallengeDailyLogRepository,
    private val transactionRepository: TransactionRepository
) : ChallengeService {

    @Transactional
    override fun createChallenge(memberId: Long, dto: ChallengeCreateReqDto): ChallengeRespDto {
        // Validate date range
        if (dto.endDate.isBefore(dto.startDate)) {
            throw ChallengeException(ChallengeErrorCode.INVALID_DATE_RANGE)
        }

        // Check max 1 active challenge per member
        val existing = challengeRepository.findByMemberIdAndStatus(memberId, ChallengeStatus.ACTIVE)
        if (existing != null) {
            throw ChallengeException(ChallengeErrorCode.ACTIVE_CHALLENGE_EXISTS)
        }

        val challenge = Challenge(
            memberId = memberId,
            type = dto.type,
            startDate = dto.startDate,
            endDate = dto.endDate,
            targetDays = dto.targetDays
        )

        val saved = challengeRepository.save(challenge)
        return ChallengeRespDto.fromEntity(saved)
    }

    @Transactional(readOnly = true)
    override fun getActiveChallenge(memberId: Long): ChallengeRespDto? {
        val challenge = challengeRepository.findByMemberIdAndStatus(memberId, ChallengeStatus.ACTIVE)
            ?: return null
        return ChallengeRespDto.fromEntity(challenge)
    }

    @Transactional(readOnly = true)
    override fun getChallengeDetail(memberId: Long, challengeId: Long): ChallengeDetailRespDto {
        val challenge = challengeRepository.findById(challengeId)
            .orElseThrow { ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND) }

        if (challenge.memberId != memberId) {
            throw ChallengeException(ChallengeErrorCode.CHALLENGE_ACCESS_DENIED)
        }

        val dailyLogs = challengeDailyLogRepository.findByChallengeIdOrderByLogDateDesc(challengeId)
            .map { DailyLogRespDto.fromEntity(it) }

        return ChallengeDetailRespDto(
            challenge = ChallengeRespDto.fromEntity(challenge),
            dailyLogs = dailyLogs
        )
    }

    @Transactional(readOnly = true)
    override fun getChallengeHistory(memberId: Long, page: Int, size: Int): Page<ChallengeRespDto> {
        val pageable = PageRequest.of(page, size)
        return challengeRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId, pageable)
            .map { ChallengeRespDto.fromEntity(it) }
    }

    @Transactional
    override fun cancelChallenge(memberId: Long, challengeId: Long) {
        val challenge = challengeRepository.findById(challengeId)
            .orElseThrow { ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND) }

        if (challenge.memberId != memberId) {
            throw ChallengeException(ChallengeErrorCode.CHALLENGE_ACCESS_DENIED)
        }

        if (challenge.status != ChallengeStatus.ACTIVE) {
            throw ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_ACTIVE)
        }

        challenge.status = ChallengeStatus.CANCELLED
        challenge.updatedAt = LocalDateTime.now()
        challengeRepository.save(challenge)
    }

    @Transactional
    override fun processDaily() {
        val yesterday = LocalDate.now().minusDays(1)
        val activeChallenges = challengeRepository.findAllByStatus(ChallengeStatus.ACTIVE)

        for (challenge in activeChallenges) {
            try {
                processSingleChallenge(challenge, yesterday)
            } catch (e: Exception) {
                log.error("챌린지 일일 처리 실패 - challengeId: ${challenge.id}, memberId: ${challenge.memberId}", e)
            }
        }
    }

    private fun processSingleChallenge(challenge: Challenge, date: LocalDate) {
        // Only process if date is within challenge period
        if (date.isBefore(challenge.startDate) || date.isAfter(challenge.endDate)) {
            // If past end date, check completion
            if (date.isAfter(challenge.endDate)) {
                if (challenge.achievedDays >= challenge.targetDays) {
                    challenge.status = ChallengeStatus.COMPLETED
                } else {
                    challenge.status = ChallengeStatus.FAILED
                }
                challenge.updatedAt = LocalDateTime.now()
                challengeRepository.save(challenge)
            }
            return
        }

        // Check if already logged for this date
        val existingLog = challengeDailyLogRepository.findByChallengeIdAndLogDate(challenge.id!!, date)
        if (existingLog != null) return

        // Check OUTCOME transactions for this member on this date
        val outcomeResults = transactionRepository.sumByMemberIdAndDateRange(
            challenge.memberId, date, date
        )

        var outcomeAmount = 0
        for (row in outcomeResults) {
            val txType = row[0].toString()
            if (txType == "OUTCOME") {
                outcomeAmount = (row[1] as Number).toInt()
                break
            }
        }

        val isNoSpend = outcomeAmount == 0

        // Create daily log
        val dailyLog = ChallengeDailyLog(
            challengeId = challenge.id!!,
            logDate = date,
            isNoSpend = isNoSpend,
            outcomeAmount = outcomeAmount
        )
        challengeDailyLogRepository.save(dailyLog)

        // Update achieved days if no-spend
        if (isNoSpend) {
            challenge.achievedDays += 1
        }

        // Check if challenge is completed (achieved target)
        if (challenge.achievedDays >= challenge.targetDays) {
            challenge.status = ChallengeStatus.COMPLETED
        }

        challenge.updatedAt = LocalDateTime.now()
        challengeRepository.save(challenge)
    }
}
