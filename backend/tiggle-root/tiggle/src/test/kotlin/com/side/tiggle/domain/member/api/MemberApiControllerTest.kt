package com.side.tiggle.domain.member.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.member.dto.req.MemberCreateReqDto
import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.dto.resp.MemberListRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.member.service.MemberService
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class MemberApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @MockitoBean private val memberService: MemberService,
) : StringSpec() {

    override suspend fun beforeEach(testCase: TestCase) {
        reset(memberService)
    }

    private fun createMockMemberRespDto(
        id: Long = 1L,
        email: String = "test@example.com",
        nickname: String = "테스트유저",
        profileUrl: String? = null,
        birth: LocalDate? = LocalDate.of(1990, 1, 1)
    ): MemberRespDto {
        return MemberRespDto(
            id = id,
            email = email,
            profileUrl = profileUrl,
            nickname = nickname,
            birth = birth
        )
    }

    init {
        "POST /api/v1/member - 회원 생성 성공" {
            // given
            val request = MemberCreateReqDto(
                email = "newuser@example.com",
                profileUrl = "https://example.com/profile.jpg",
                nickname = "새로운유저",
                birth = LocalDate.of(1995, 3, 15)
            )

            doNothing().`when`(memberService).createMember(any())

            // when & then
            mockMvc.perform(
                post("/api/v1/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("멤버가 생성되었습니다."))

            verify(memberService).createMember(any<MemberCreateReqDto>())
        }

        "GET /api/v1/member/{id} - 회원 조회 성공" {
            // given
            val memberId = 1L
            val expectedResponse = createMockMemberRespDto(memberId, "user@test.com", "조회유저")

            given(memberService.getMember(memberId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/member/$memberId")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(memberId))
                .andExpect(jsonPath("$.data.email").value("user@test.com"))
                .andExpect(jsonPath("$.data.nickname").value("조회유저"))

            verify(memberService).getMember(memberId)
        }

        "GET /api/v1/member/all - 전체 회원 조회 성공" {
            // given
            val memberList = listOf(
                createMockMemberRespDto(1L, "user1@test.com", "유저1"),
                createMockMemberRespDto(2L, "user2@test.com", "유저2"),
                createMockMemberRespDto(3L, "user3@test.com", "유저3")
            )
            val expectedResponse = MemberListRespDto(memberList)

            given(memberService.getAllMember()).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/member/all")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.members").isArray)
                .andExpect(jsonPath("$.data.members.length()").value(3))
                .andExpect(jsonPath("$.data.members[0].nickname").value("유저1"))
                .andExpect(jsonPath("$.data.members[1].nickname").value("유저2"))
                .andExpect(jsonPath("$.data.members[2].nickname").value("유저3"))

            verify(memberService).getAllMember()
        }

        "GET /api/v1/member/me - 내 정보 조회 성공" {
            // given
            val memberId = 1L // 테스트 환경에서 헤더값
            val expectedResponse = createMockMemberRespDto(1L, "me@test.com", "내정보")

            given(memberService.getMember(memberId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/member/me")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("me@test.com"))
                .andExpect(jsonPath("$.data.nickname").value("내정보"))

            verify(memberService).getMember(memberId)
        }

        "PUT /api/v1/member/me - 프로필 업데이트 성공 (DTO + 파일)" {
            // given
            val memberId = 1L
            val updateRequest = MemberUpdateReqDto(
                nickname = "수정된닉네임",
                birth = LocalDate.of(1992, 5, 10)
            )

            val profileImage = MockMultipartFile(
                "multipartFile",
                "profile.jpg",
                "image/jpeg",
                "test image data".toByteArray()
            )

            val dtoFile = MockMultipartFile(
                "dto",
                "",
                "application/json",
                objectMapper.writeValueAsString(updateRequest).toByteArray()
            )

            doNothing().`when`(memberService).updateMember(any(), any(), any())

            // when & then
            mockMvc.perform(
                multipart("/api/v1/member/me")
                    .file(profileImage)
                    .file(dtoFile)
                    .header("x-member-id", "1")
                    .with { request ->
                        request.method = "PUT"
                        request
                    }
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("멤버 정보가 수정되었습니다."))

            verify(memberService).updateMember(eq(memberId), any<MemberUpdateReqDto>(), any<MultipartFile>())
        }

        "PUT /api/v1/member/me - 프로필 업데이트 성공 (DTO만)" {
            // given
            val memberId = 1L
            val updateRequest = MemberUpdateReqDto(
                nickname = "닉네임만수정",
                birth = null
            )

            val dtoFile = MockMultipartFile(
                "dto",
                "",
                "application/json",
                objectMapper.writeValueAsString(updateRequest).toByteArray()
            )

            doNothing().`when`(memberService).updateMember(any(), any(), any())

            // when & then
            mockMvc.perform(
                multipart("/api/v1/member/me")
                    .file(dtoFile)
                    .header("x-member-id", "1")
                    .with { request ->
                        request.method = "PUT"
                        request
                    }
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("멤버 정보가 수정되었습니다."))

            verify(memberService).updateMember(eq(memberId), any<MemberUpdateReqDto>(), eq(null))
        }

        "PUT /api/v1/member/me - 프로필 업데이트 성공 (파일만)" {
            // given
            val memberId = 1L

            val profileImage = MockMultipartFile(
                "multipartFile",
                "new-profile.png",
                "image/png",
                "new profile image data".toByteArray()
            )

            val emptyDto = MockMultipartFile(
                "dto",
                "",
                "application/json",
                "{}".toByteArray()
            )

            doNothing().`when`(memberService).updateMember(any(), any(), any())

            // when & then
            mockMvc.perform(
                multipart("/api/v1/member/me")
                    .file(profileImage)
                    .file(emptyDto)
                    .header("x-member-id", "1")
                    .with { request ->
                        request.method = "PUT"
                        request
                    }
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("멤버 정보가 수정되었습니다."))

            verify(memberService).updateMember(eq(memberId), any(), any<MultipartFile>())
        }
    }
}