package com.side.tiggle.domain.member.service

import com.side.tiggle.batch.Example
import com.side.tiggle.batch.ExampleRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MultiSchemaTest(
    private val exampleRepository: ExampleRepository
): BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    init {
        given("multi schema - batch 접근 테스트") {
            print(exampleRepository.findById(1L))
            exampleRepository.count() shouldBe 1
        }
    }


}