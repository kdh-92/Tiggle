//package com.side.tiggle.domain.member.api
//
//import com.side.tiggle.domain.member.dto.controller.MemberRequestDto
//import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
//import com.side.tiggle.domain.member.model.Member
//import com.side.tiggle.domain.member.repository.MemberRepository
//import com.side.tiggle.util.MockMvcSupport
//import com.side.tiggle.util.fromJson
//import com.side.tiggle.util.toJson
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.shouldBe
//import io.kotest.matchers.shouldNotBe
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import java.time.LocalDate
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//class MemberApiControllerTest(
//    private val mockMvcSupport: MockMvcSupport,
//    private val memberRepository: MemberRepository
//) : BehaviorSpec({
//
//    given("회원 생성 요청") {
//        val url = "/api/v1/member"
//        val request = MemberRequestDto(
//            email = "test@test.com",
//            profileUrl = "http://test.com",
//            nickname = "test",
//            birth = LocalDate.of(1990,1 ,1)
//        )
//
//        `when`("회원 가입 API를 호출한다") {
//            val result = mockMvcSupport.postAndReturn(
//                url = url,
//                request = request,
//            )
//
//            then("201 CREATED") {
//                result.status shouldBe 201
//
//                val body = result.contentAsString.toJson<MemberResponseDto>()
//                body.email shouldBe request.email
//                body.nickname shouldBe request.nickname
//
//                val entity = memberRepository.findByEmail(request.email)
//                entity shouldNotBe null
//                entity!!.email shouldBe request.email
//            }
//        }
//
//        `when`("동일한 이메일로 가입을 시도한다") {
//
//        }
//    }
//
//
//    given("가입이 된 상태에서") {
//        val member = Member(
//            email = "test@test.com",
//            profileUrl = "http://test.com",
//            nickname = "test",
//            birth = LocalDate.of(1990,1 ,1),
//            providerId = "kakao",
//            provider = "kakao"
//        )
//        val memberId = memberRepository.save(member).id
//
//        `when`("me 조회 요청을 한다") {
//            val url = "/api/v1/member/me"
//            val result = mockMvcSupport.getAndReturn(url, headers = mapOf("x-member-id" to memberId))
//
//            then("200 OK") {
//                result.status shouldBe 200
//
//                val body = result.contentAsString.toJson<MemberResponseDto>()
//                print(body)
//
//                body.email shouldBe member.email
//                body.nickname shouldBe member.nickname
//                body.profileUrl shouldBe member.profileUrl
//            }
//        }
//    }
//})