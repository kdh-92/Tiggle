package com.side.tiggle.domain.challenge.dto.resp

data class ChallengeDetailRespDto(
    val challenge: ChallengeRespDto,
    val dailyLogs: List<DailyLogRespDto>
)
