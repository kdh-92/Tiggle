package com.side.tiggle.domain.transaction

import com.side.tiggle.domain.asset.model.Asset
import com.side.tiggle.domain.asset.repository.AssetRepository
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.model.CategoryType
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.transaction.dto.TransactionDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.model.TransactionType
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.util.MockMvcSupport
import com.side.tiggle.util.fromJson
import com.side.tiggle.util.toJson
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TransactionApiController(
    private val mockMvc: MockMvc,
    private val mockMvcSupport: MockMvcSupport,
    private val memberRepository: MemberRepository,
    private val assetRepository: AssetRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
): BehaviorSpec({
    val memberId = 1L
    val member = Member(
        email = "asdf@asdf.com",
        nickname = "asdf",
        providerId = "kakao",
        provider = "kakao",
        profileUrl = "Asdf"
    ).apply {
        id = memberId
    }

    val asset = Asset(
        name = "asdf",
        defaults = true
    ).apply {
        id = 1L
    }

    val category = Category(
        name = "asdfasdf",
        defaults = true,
        type = CategoryType.OUTCOME
    ).apply {
        id = 1L
    }

    val tx = getTransaction(member, asset, category)

    beforeTest {
        memberRepository.save(member)
        assetRepository.save(asset)
        categoryRepository.save(category)
        transactionRepository.save(tx)
    }

    given("트랜잭션 CRUD") {
        val request = TransactionDto(
            memberId = memberId,
            parentId = null,
            assetId = asset.id!!,
            categoryId = category.id!!,
            type = TransactionType.OUTCOME,
            imageUrl = "imageUrl",
            amount = 1,
            date = LocalDate.now(),
            content = "내용",
            reason = "이유",
            tagNames = "asdf,asdf"
        )

        val url = "/api/v1/transaction"
        val txForCreate = getTransaction(member, asset, category).apply { id = 10 }
        val txForUpdate = getTransaction(member, asset, category).apply { id = 20 }
        val txForDelete = getTransaction(member, asset, category).apply { id = 30 }
        transactionRepository.saveAll(
            listOf(txForCreate, txForUpdate, txForDelete)
        )

        `when`("POST $url") {
            val image = MockMultipartFile("multipartFile", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".toByteArray())
            val dto = MockMultipartFile("dto", request.fromJson().toByteArray())
            val result = mockMvc.perform(
                multipart(url)
                    .file(image)
                    .file(dto)
                    .header("x-member-id", memberId)
            )
                .andDo(MockMvcResultHandlers.print())
                .andReturn().response

            then("201 CREATED") {
                result.status shouldBe 201

                val body = result.contentAsString.toJson<TransactionRespDto>()
                print(body)

                body.amount shouldBe request.amount
                body.member.email shouldBe member.email
                body.asset.name shouldBe asset.name
                body.category.name shouldBe category.name
            }
        }

        `when`("GET $url/${tx.id!!}") {
            val result = mockMvcSupport.getAndReturn(
                url = "$url/${tx.id!!}",
                headers = mapOf("x-member-id" to memberId),
            )

            then("200 OK") {
                result.status shouldBe 200
                val body = result.contentAsString.toJson<TransactionRespDto>()

                body.amount shouldBe tx.amount
                body.member.email shouldBe member.email
                body.id shouldBe tx.id!!
                body.asset.name shouldBe asset.name
                body.category.name shouldBe category.name
            }
        }

        `when`("DELETE $url/${tx.id!!}") {

        }
    }
}) {
    companion object {
        fun getTransaction(member: Member, asset: Asset, category: Category): Transaction {
            return Transaction(
                member = member,
                asset = asset,
                category = category,
                amount = 100,
                content = "asdfasdf",
                date = LocalDate.now(),
                reason = "zxcvzxcv",
                type = TransactionType.OUTCOME
            )
        }
    }
}