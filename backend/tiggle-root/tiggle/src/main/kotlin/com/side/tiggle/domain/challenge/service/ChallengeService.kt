package com.side.tiggle.domain.challenge.service

import com.side.tiggle.domain.challenge.dto.req.ChallengeCreateReqDto
import com.side.tiggle.domain.challenge.dto.resp.ChallengeDetailRespDto
import com.side.tiggle.domain.challenge.dto.resp.ChallengeRespDto
import org.springframework.data.domain.Page

interface ChallengeService {

    fun createChallenge(memberId: Long, dto: ChallengeCreateReqDto): ChallengeRespDto

    fun getActiveChallenge(memberId: Long): ChallengeRespDto?

    fun getChallengeDetail(memberId: Long, challengeId: Long): ChallengeDetailRespDto

    fun getChallengeHistory(memberId: Long, page: Int, size: Int): Page<ChallengeRespDto>

    fun cancelChallenge(memberId: Long, challengeId: Long)

    fun processDaily()
}
