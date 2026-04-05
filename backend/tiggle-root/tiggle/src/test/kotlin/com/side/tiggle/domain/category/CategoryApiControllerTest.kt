//package com.side.tiggle.domain.category
//
//import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
//import com.side.tiggle.domain.category.model.Category
//import com.side.tiggle.domain.category.repository.CategoryRepository
//import com.side.tiggle.domain.member.model.Member
//import com.side.tiggle.domain.member.repository.MemberRepository
//import com.side.tiggle.util.MockMvcSupport
//import com.side.tiggle.util.toJson
//import io.kotest.core.spec.style.BehaviorSpec
//import io.kotest.matchers.nulls.shouldNotBeNull
//import io.kotest.matchers.shouldBe
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import java.time.LocalDate
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//class CategoryApiControllerTest(
//    private val mockMvcSupport: MockMvcSupport,
//    private val categoryRepository: CategoryRepository,
//    private val memberRepository: MemberRepository
//) : BehaviorSpec({
//
//    lateinit var testMember: Member
//
//    beforeEach {
//        testMember = memberRepository.save(
//            Member(
//                email = "test@test.com",
//                profileUrl = "https://example.com/profile.jpg",
//                nickname = "TestUser",
//                birth = LocalDate.of(1990, 1, 1)
//            )
//        )
//    }
//
//    given("카테고리 생성 요청") {
//        val request = CategoryDto(
//            name = "asdfasdf",
//            defaults = false,
//            memberId = testMember.id
//        )
//
//        `when`("POST /api/v1/category") {
//            val url = "/api/v1/category"
//            val result = mockMvcSupport.postAndReturn(
//                url = url,
//                request = request,
//            )
//
//            then("201 CREATED") {
//                result.status shouldBe 201
//                val body = result.contentAsString.toJson<CategoryRespDto>()
//
//                body.shouldNotBeNull()
//                body.name shouldBe request.name
//
//                val responseId = requireNotNull(body.id)
//
//                val entity = categoryRepository.findById(responseId).orElse(null)
//                entity.shouldNotBeNull()
//                entity.name shouldBe request.name
//            }
//        }
//    }
//
//    given("생성된 카테고리가 있을 때") {
//        val c1 = Category(
//            name = "c1", defaults = true, member = testMember
//        )
//        val c2 = Category(
//            name = "c2", defaults = true, member = testMember
//        )
//        val c3 = Category(
//            name = "c3", defaults = true, member = testMember
//        )
//        categoryRepository.saveAll(listOf(c1, c2, c3))
//
//        `when`("GET /api/v1/category/all") {
//            val url = "/api/v1/category/all"
//            val result = mockMvcSupport.getAndReturn(url)
//
//            then("200 OK") {
//                result.status shouldBe 200
//
//                val body = result.contentAsString.toJson<List<CategoryRespDto>>()
//                print(body)
//                body.size shouldBe 3
//                body.none { it.name == c1.name } shouldBe false
//                body.none { it.name == c2.name } shouldBe false
//                body.none { it.name == c3.name } shouldBe false
//            }
//        }
//
//        `when`("GET /api/v1/category/{id}") {
//            val url = "/api/v1/category/${c1.id!!}"
//            val result = mockMvcSupport.getAndReturn(url)
//
//            then("200 OK") {
//                result.status shouldBe 200
//
//                val body = result.contentAsString.toJson<CategoryRespDto>()
//                print(body)
//                body.name shouldBe c1.name
//            }
//        }
//
//        `when`("PUT /api/v1/category/{id}") {
//            val url = "/api/v1/category/${c1.id!!}"
//        }
//    }
//})
