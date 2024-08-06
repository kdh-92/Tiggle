package com.side.tiggle.domain.category

import com.side.tiggle.domain.category.dto.CategoryDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.util.MockMvcSupport
import com.side.tiggle.util.toJson
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoryApiControllerTest(
    private val mockMvcSupport: MockMvcSupport,
    private val categoryRepository: CategoryRepository
) : BehaviorSpec({

    given("카테고리 생성 요청") {
        val request = CategoryDto(
            name = "asdfasdf",
            defaults = true
        )

        `when`("GET /api/v1/category") {
            val url = "/api/v1/category"
            val result = mockMvcSupport.postAndReturn(
                url = url,
                request = request,
            )

            then("201 CREATED") {
                result.status shouldBe 201
                val body = result.contentAsString.toJson<CategoryRespDto>()

                body.name shouldBe request.name

                val entity = categoryRepository.findById(body.id).get()
                entity shouldNotBe null
                entity.name shouldBe request.name
            }
        }
    }

    given("생성된 카테고리가 있을 때") {
        val c1 = Category(
            name = "c1", defaults = true
        )
        val c2 = Category(
            name = "c2", defaults = true
        )
        val c3 = Category(
            name = "c3", defaults = true
        )
        categoryRepository.saveAll(listOf(c1, c2, c3))

        `when`("GET /api/v1/category/all") {
            val url = "/api/v1/category/all"
            val result = mockMvcSupport.getAndReturn(url)

            then("200 OK") {
                result.status shouldBe 200

                val body = result.contentAsString.toJson<List<CategoryRespDto>>()
                print(body)
                body.size shouldBe 3
                body.none { it.name == c1.name } shouldBe false
                body.none { it.name == c2.name } shouldBe false
                body.none { it.name == c3.name } shouldBe false
            }
        }

        `when`("GET /api/v1/category/{id}") {
            val url = "/api/v1/category/${c1.id!!}"
            val result = mockMvcSupport.getAndReturn(url)

            then("200 OK") {
                result.status shouldBe 200

                val body = result.contentAsString.toJson<CategoryRespDto>()
                print(body)
                body.name shouldBe c1.name
            }
        }

        `when`("PUT /api/v1/category/{id}") {
            val url = "/api/v1/category/${c1.id!!}"
        }
    }
})