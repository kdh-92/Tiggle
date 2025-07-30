package com.side.tiggle.domain.transaction.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import com.side.tiggle.domain.transaction.exception.TransactionException
import com.side.tiggle.domain.transaction.exception.error.TransactionErrorCode
import com.side.tiggle.domain.transaction.mapper.TransactionMapper
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.domain.transaction.utils.TransactionFileUploadUtil
import com.side.tiggle.support.factory.TestMemberFactory
import com.side.tiggle.support.factory.TestTransactionFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class TransactionServiceImplTest : StringSpec({

    val transactionRepository: TransactionRepository = mockk()
    val memberService: MemberService = mockk()
    val categoryService: CategoryService = mockk()
    val transactionMapper: TransactionMapper = mockk()
    val transactionFileUploadUtil: TransactionFileUploadUtil = mockk()
    val objectMapper: ObjectMapper = mockk()

    val transactionService: TransactionService = TransactionServiceImpl(
        transactionRepository,
        memberService,
        categoryService,
        transactionMapper,
        transactionFileUploadUtil,
        objectMapper
    )

    beforeEach {
        clearAllMocks()
    }

    "거래를 생성합니다" {
        // given
        val memberId = 1L
        val categoryId = 2L
        val dto = TransactionCreateReqDto(
            categoryId = categoryId,
            imageUrls = null,
            amount = 10000,
            date = LocalDate.now(),
            content = "점심식사",
            reason = "회사 근처 식당에서 점심",
            tagNames = listOf("식비", "점심")
        )
        val files = listOf<MultipartFile>(mockk())
        val uploadedPaths = listOf("/path/to/image.jpg")
        val imageUrlsJson = """["/path/to/image.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns uploadedPaths
        every { objectMapper.writeValueAsString(uploadedPaths) } returns imageUrlsJson
        every { memberService.getMemberReference(memberId) } returns member
        every { categoryService.getCategoryReference(categoryId) } returns category
        every { transactionRepository.save(any()) } returns transaction

        // when
        transactionService.createTransaction(memberId, dto, files)

        // then
        verify(exactly = 1) { transactionFileUploadUtil.uploadTransactionImages(files) }
        verify(exactly = 1) { memberService.getMemberReference(memberId) }
        verify(exactly = 1) { categoryService.getCategoryReference(categoryId) }
        verify(exactly = 1) { transactionRepository.save(any()) }
        dto.imageUrls shouldBe imageUrlsJson
    }

    "거래 생성 시 파일 업로드 실패하면 임시 파일을 정리합니다" {
        // given
        val memberId = 1L
        val dto = TransactionCreateReqDto(
            categoryId = 2L,
            imageUrls = null,
            amount = 10000,
            date = LocalDate.now(),
            content = "점심식사",
            reason = "회사 근처 식당에서 점심",
            tagNames = null
        )
        val files = listOf<MultipartFile>(mockk())
        val uploadedPaths = listOf("/path/to/image.jpg")

        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns uploadedPaths
        every { objectMapper.writeValueAsString(uploadedPaths) } returns """["/path/to/image.jpg"]"""
        every { memberService.getMemberReference(memberId) } throws RuntimeException("Member not found")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } just Runs

        mockkStatic("java.nio.file.Files")
        every { java.nio.file.Files.deleteIfExists(any<Path>()) } returns true

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.createTransaction(memberId, dto, files)
        }

        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/image.jpg")) }
    }

    "거래를 수정합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val dto = TransactionUpdateReqDto(
            amount = 15000,
            date = LocalDate.now().plusDays(1),
            content = "저녁식사",
            reason = "회식",
            categoryId = 2L,
            tagNames = listOf("회식", "저녁")
        )

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionRepository.save(transaction) } returns transaction

        // when
        transactionService.updateTransaction(memberId, transactionId, dto)

        // then
        transaction.amount shouldBe 15000
        transaction.date shouldBe dto.date
        transaction.content shouldBe "저녁식사"
        transaction.reason shouldBe "회식"
        transaction.tagNames shouldBe listOf("회식", "저녁")
        verify { transactionRepository.save(transaction) }
    }

    "거래 수정 시 존재하지 않는 거래라면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val dto = TransactionUpdateReqDto(
            amount = 15000,
            date = LocalDate.now(),
            content = "저녁식사",
            reason = "회식",
            categoryId = 2L,
            tagNames = null
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.updateTransaction(memberId, transactionId, dto)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 수정 시 권한이 없다면 예외를 던집니다" {
        // given
        val memberId = 1L
        val otherMemberId = 2L
        val transactionId = 10L
        val dto = TransactionUpdateReqDto(
            amount = 15000,
            date = LocalDate.now(),
            content = "저녁식사",
            reason = "회식",
            categoryId = 2L,
            tagNames = null
        )

        val otherMember = TestMemberFactory.create(id = otherMemberId)
        val category = Category("식비", false, otherMemberId)
        val transaction = TestTransactionFactory.create(
            member = otherMember,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.updateTransaction(memberId, transactionId, dto)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_ACCESS_DENIED
    }

    "거래를 삭제합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findById(transactionId) } returns Optional.of(transaction)
        every { transactionRepository.delete(transaction) } just Runs

        // when
        transactionService.deleteTransaction(memberId, transactionId)

        // then
        verify { transactionRepository.delete(transaction) }
    }

    "거래 삭제 시 존재하지 않는 거래라면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L

        every { transactionRepository.findById(transactionId) } returns Optional.empty()

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransaction(memberId, transactionId)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 삭제 시 권한이 없다면 예외를 던집니다" {
        // given
        val memberId = 1L
        val otherMemberId = 2L
        val transactionId = 10L

        val otherMember = TestMemberFactory.create(id = otherMemberId)
        val category = Category("식비", false, otherMemberId)
        val transaction = TestTransactionFactory.create(
            member = otherMember,
            category = category
        )

        every { transactionRepository.findById(transactionId) } returns Optional.of(transaction)

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransaction(memberId, transactionId)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_ACCESS_DENIED
    }

    "거래 상세 정보를 조회합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, 1L).apply { id = 2L }
        val transaction = TestTransactionFactory.create(
            id = transactionId,
            member = member,
            category = category
        ).apply {
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
        }

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when
        val result = transactionService.getTransactionDetail(transactionId)

        // then
        result.id shouldBe transactionId
        result.content shouldBe "커피커피"
        result.amount shouldBe 10000
    }

    "거래 상세 조회 시 존재하지 않는 거래라면 예외를 던집니다" {
        // given
        val transactionId = 999L

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.getTransactionDetail(transactionId)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "내부용 거래 정보를 조회합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, 1L).apply { id = 2L }
        val transaction = TestTransactionFactory.create(
            id = transactionId,
            member = member,
            category = category
        ).apply {
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
        }

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when
        val result = transactionService.getTransactionOrThrow(transactionId)

        // then
        result shouldBe TransactionInfo.fromEntity(transaction)
    }

    "전체 거래 목록을 페이징으로 조회합니다" {
        // given
        val memberId = 1L
        val pageSize = 10
        val index = 0
        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, 1L).apply { id = 2L }
        val transaction = TestTransactionFactory.create(
            id = 1L,
            member = member,
            category = category
        )

        val transactions = listOf(transaction)
        val page = PageImpl(transactions, PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt")), 1)
        val mockRespDto = mockk<TransactionRespDto> {
            every { id } returns 1L
            every { content } returns "커피커피"
        }
        val dtoWithCount = TransactionDtoWithCount(mockRespDto, 5, 2, 3)

        every { transactionRepository.findAllWithMemberAndCategoryPaged(any()) } returns page
        every { transactionMapper.toDtoWithCount(transaction) } returns dtoWithCount

        // when
        val result = transactionService.getCountOffsetTransaction(pageSize, index)

        // then
        result.transactions.size shouldBe 1
        result.pageSize shouldBe pageSize
        result.pageNumber shouldBe index
    }

    "전체 거래 목록 조회 시 데이터가 없으면 예외를 던집니다" {
        // given
        val pageSize = 10
        val index = 0
        val emptyPage = PageImpl<Transaction>(emptyList(), PageRequest.of(index, pageSize), 0)

        every { transactionRepository.findAllWithMemberAndCategoryPaged(any()) } returns emptyPage

        // when & then
        shouldThrow<TransactionException> {
            transactionService.getCountOffsetTransaction(pageSize, index)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "회원별 거래 목록을 필터링하여 조회합니다" {
        // given
        val memberId = 1L
        val count = 10
        val offset = 0
        val startDate = LocalDate.now().minusDays(30)
        val endDate = LocalDate.now()
        val categoryIds = listOf(1L, 2L)
        val tagNames = listOf("커피")
        val tagNamesJson = """["커피"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("커피", false, memberId).apply { id = 2L }
        val transaction = TestTransactionFactory.create(
            id = 1L,
            member = member,
            category = category
        )

        val transactions = listOf(transaction)
        val page = PageImpl(transactions, PageRequest.of(offset, count), 1)
        val mockRespDto = mockk<TransactionRespDto> {
            every { id } returns 1L
            every { content } returns "커피커피"
        }
        val dtoWithCount = TransactionDtoWithCount(mockRespDto, 5, 2, 3)

        every { objectMapper.writeValueAsString(tagNames) } returns tagNamesJson
        every { transactionRepository.findByMemberIdWithFilters(memberId, startDate, endDate, categoryIds, tagNamesJson, any()) } returns page
        every { transactionMapper.toDtoWithCount(transaction) } returns dtoWithCount

        // when
        val result = transactionService.getMemberCountOffsetTransaction(memberId, count, offset, startDate, endDate, categoryIds, tagNames)

        // then
        result.transactions.size shouldBe 1
        result.pageSize shouldBe count
        result.pageNumber shouldBe offset
    }

    "회원별 거래 목록 조회 시 태그가 없으면 null로 처리합니다" {
        // given
        val memberId = 1L
        val count = 10
        val offset = 0

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId).apply { id = 2L }
        val transaction = TestTransactionFactory.create(
            id = 1L,
            member = member,
            category = category,
            tagNames = null
        )

        val transactions = listOf(transaction)
        val page = PageImpl(transactions, PageRequest.of(offset, count), 1)
        val mockRespDto = mockk<TransactionRespDto> {
            every { id } returns 1L
            every { content } returns "커피커피"
        }
        val dtoWithCount = TransactionDtoWithCount(mockRespDto, 5, 2, 3)

        every { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) } returns page
        every { transactionMapper.toDtoWithCount(transaction) } returns dtoWithCount

        // when
        transactionService.getMemberCountOffsetTransaction(memberId, count, offset, null, null, null, null)

        // then
        verify { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) }
    }

    "거래에 사진을 추가합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())
        val existingPaths = listOf("/path/to/existing.jpg")
        val newPaths = listOf("/path/to/new.jpg")
        val existingJson = """["/path/to/existing.jpg"]"""
        val allPathsJson = """["/path/to/existing.jpg","/path/to/new.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = existingJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns newPaths
        every { objectMapper.readValue(existingJson, Array<String>::class.java) } returns existingPaths.toTypedArray()
        every { objectMapper.writeValueAsString(existingPaths + newPaths) } returns allPathsJson
        every { transactionRepository.save(transaction) } returns transaction

        // when
        transactionService.addTransactionPhotos(memberId, transactionId, files)

        // then
        transaction.imageUrls shouldBe allPathsJson
        verify { transactionRepository.save(transaction) }
    }

    "거래 사진 추가 시 권한이 없다면 예외를 던집니다" {
        // given
        val memberId = 1L
        val otherMemberId = 2L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())

        val otherMember = TestMemberFactory.create(id = otherMemberId)
        val category = Category("식비", false, otherMemberId)
        val transaction = TestTransactionFactory.create(
            member = otherMember,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_ACCESS_DENIED
    }

    "거래 사진을 삭제합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 0
        val imagePaths = mutableListOf("/path/to/image1.jpg", "/path/to/image2.jpg")
        val imagePathsJson = """["/path/to/image1.jpg","/path/to/image2.jpg"]"""
        val updatedPathsJson = """["/path/to/image2.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = imagePathsJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { objectMapper.readValue(imagePathsJson, Array<String>::class.java) } returns imagePaths.toTypedArray()
        every { transactionFileUploadUtil.deleteTransactionImage("/path/to/image1.jpg") } returns true
        every { objectMapper.writeValueAsString(listOf("/path/to/image2.jpg")) } returns updatedPathsJson
        every { transactionRepository.save(transaction) } returns transaction

        // when
        transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)

        // then
        transaction.imageUrls shouldBe updatedPathsJson
        verify { transactionFileUploadUtil.deleteTransactionImage("/path/to/image1.jpg") }
        verify { transactionRepository.save(transaction) }
    }

    "거래 사진 삭제 시 사진이 1장뿐이면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 0
        val imagePaths = mutableListOf("/path/to/image1.jpg")
        val imagePathsJson = """["/path/to/image1.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = imagePathsJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { objectMapper.readValue(imagePathsJson, Array<String>::class.java) } returns imagePaths.toTypedArray()

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.MINIMUM_PHOTO_REQUIRED
    }

    "거래 사진 삭제 시 잘못된 인덱스면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 5
        val imagePaths = mutableListOf("/path/to/image1.jpg", "/path/to/image2.jpg")
        val imagePathsJson = """["/path/to/image1.jpg","/path/to/image2.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = imagePathsJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { objectMapper.readValue(imagePathsJson, Array<String>::class.java) } returns imagePaths.toTypedArray()

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }

    "거래 사진 삭제 시 잘못된 인덱스면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 5
        val imagePaths = mutableListOf("/path/to/image1.jpg", "/path/to/image2.jpg")
        val imagePathsJson = """["/path/to/image1.jpg","/path/to/image2.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = imagePathsJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { objectMapper.readValue(imagePathsJson, Array<String>::class.java) } returns imagePaths.toTypedArray()

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }

    "거래 사진 추가 시 파일 업로드 실패하면 임시 파일과 폴더를 정리합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())
        val newPaths = listOf("/path/to/new.jpg")

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns newPaths
        every { objectMapper.readValue(any<String>(), Array<String>::class.java) } throws RuntimeException("JSON 파싱 실패")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } just Runs

        mockkStatic("java.nio.file.Files")
        every { java.nio.file.Files.deleteIfExists(any<Path>()) } returns true

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }

        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new.jpg")) }
        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
    }

    "거래 사진 삭제 시 imageUrls가 비어있으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 0

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = null
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }

    "거래 사진 삭제 시 음수 인덱스면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = -1
        val imagePaths = mutableListOf("/path/to/image1.jpg", "/path/to/image2.jpg")
        val imagePathsJson = """["/path/to/image1.jpg","/path/to/image2.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = imagePathsJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { objectMapper.readValue(imagePathsJson, Array<String>::class.java) } returns imagePaths.toTypedArray()

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }

    "회원별 거래 목록 조회 시 데이터가 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val count = 10
        val offset = 0
        val emptyPage = PageImpl<Transaction>(emptyList(), PageRequest.of(offset, count), 0)

        every { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) } returns emptyPage

        // when & then
        shouldThrow<TransactionException> {
            transactionService.getMemberCountOffsetTransaction(memberId, count, offset, null, null, null, null)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 추가 시 거래를 찾을 수 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val files = listOf<MultipartFile>(mockk())

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 삭제 시 거래를 찾을 수 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val photoIndex = 0

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 삭제 시 권한이 없다면 예외를 던집니다" {
        // given
        val memberId = 1L
        val otherMemberId = 2L
        val transactionId = 10L
        val photoIndex = 0

        val otherMember = TestMemberFactory.create(id = otherMemberId)
        val category = Category("식비", false, otherMemberId)
        val transaction = TestTransactionFactory.create(
            member = otherMember,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_ACCESS_DENIED
    }

    "거래 사진 추가 시 파일 업로드 실패하면 임시 파일과 폴더를 정리합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())
        val newPaths = listOf("/path/to/new.jpg")

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns newPaths
        every { objectMapper.readValue(any<String>(), Array<String>::class.java) } throws RuntimeException("JSON 파싱 실패")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } just Runs

        mockkStatic("java.nio.file.Files")
        every { java.nio.file.Files.deleteIfExists(any<Path>()) } returns true

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }

        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new.jpg")) }
        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
    }

    "거래 사진 삭제 시 imageUrls가 비어있으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 0

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = null
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }

    "거래 사진 삭제 시 음수 인덱스면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = -1
        val imagePaths = mutableListOf("/path/to/image1.jpg", "/path/to/image2.jpg")
        val imagePathsJson = """["/path/to/image1.jpg","/path/to/image2.jpg"]"""

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = imagePathsJson
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { objectMapper.readValue(imagePathsJson, Array<String>::class.java) } returns imagePaths.toTypedArray()

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }

    "회원별 거래 목록 조회 시 데이터가 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val count = 10
        val offset = 0
        val emptyPage = PageImpl<Transaction>(emptyList(), PageRequest.of(offset, count), 0)

        every { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) } returns emptyPage

        // when & then
        shouldThrow<TransactionException> {
            transactionService.getMemberCountOffsetTransaction(memberId, count, offset, null, null, null, null)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 추가 시 거래를 찾을 수 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val files = listOf<MultipartFile>(mockk())

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 추가 시 파일 삭제 실패해도 계속 진행합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())
        val newPaths = listOf("/path/to/new1.jpg", "/path/to/new2.jpg")

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns newPaths
        every { objectMapper.readValue(any<String>(), Array<String>::class.java) } throws RuntimeException("JSON 파싱 실패")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } throws RuntimeException("폴더 삭제 실패")

        mockkStatic("java.nio.file.Files")
        every { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new1.jpg")) } throws RuntimeException("파일 삭제 실패")
        every { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new2.jpg")) } returns true

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }

        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new1.jpg")) }
        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new2.jpg")) }
        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
    }

    "거래 사진 삭제 시 거래를 찾을 수 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val photoIndex = 0

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "회원별 거래 목록 조회 시 데이터가 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val count = 10
        val offset = 0
        val emptyPage = PageImpl<Transaction>(emptyList(), PageRequest.of(offset, count), 0)

        every { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) } returns emptyPage

        // when & then
        shouldThrow<TransactionException> {
            transactionService.getMemberCountOffsetTransaction(memberId, count, offset, null, null, null, null)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 추가 시 거래를 찾을 수 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val files = listOf<MultipartFile>(mockk())

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 추가 시 파일 삭제 실패해도 계속 진행합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())
        val newPaths = listOf("/path/to/new1.jpg", "/path/to/new2.jpg")

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns newPaths
        every { objectMapper.readValue(any<String>(), Array<String>::class.java) } throws RuntimeException("JSON 파싱 실패")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } throws RuntimeException("폴더 삭제 실패")

        mockkStatic("java.nio.file.Files")
        every { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new1.jpg")) } throws RuntimeException("파일 삭제 실패")
        every { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new2.jpg")) } returns true

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }

        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new1.jpg")) }
        verify { java.nio.file.Files.deleteIfExists(Paths.get("/path/to/new2.jpg")) }
        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
    }

    "거래 사진 삭제 시 거래를 찾을 수 없으면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 999L
        val photoIndex = 0

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns null

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_NOT_FOUND
    }

    "거래 사진 삭제 시 권한이 없다면 예외를 던집니다" {
        // given
        val memberId = 1L
        val otherMemberId = 2L
        val transactionId = 10L
        val photoIndex = 0

        val otherMember = TestMemberFactory.create(id = otherMemberId)
        val category = Category("식비", false, otherMemberId)
        val transaction = TestTransactionFactory.create(
            member = otherMember,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.TRANSACTION_ACCESS_DENIED
    }

    "거래 생성 시 savedPaths가 null일 때 예외 처리를 확인합니다" {
        // given
        val memberId = 1L
        val dto = TransactionCreateReqDto(
            categoryId = 2L,
            imageUrls = null,
            amount = 10000,
            date = LocalDate.now(),
            content = "점심식사",
            reason = "회사 근처 식당에서 점심",
            tagNames = null
        )
        val files = listOf<MultipartFile>(mockk())

        every { transactionFileUploadUtil.uploadTransactionImages(files) } throws RuntimeException("업로드 실패")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } just Runs

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.createTransaction(memberId, dto, files)
        }

        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
    }

    "거래 수정 시 tagNames가 null이면 빈 리스트로 설정합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val dto = TransactionUpdateReqDto(
            amount = 15000,
            date = LocalDate.now(),
            content = "저녁식사",
            reason = "회식",
            categoryId = 2L,
            tagNames = null
        )

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionRepository.save(transaction) } returns transaction

        // when
        transactionService.updateTransaction(memberId, transactionId, dto)

        // then
        transaction.tagNames shouldBe emptyList()
    }

    "회원별 거래 목록 조회 시 tagNames가 빈 리스트면 null로 처리합니다" {
        // given
        val memberId = 1L
        val count = 10
        val offset = 0
        val emptyTags = emptyList<String>()

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        val transactions = listOf(transaction)
        val page = PageImpl(transactions, PageRequest.of(offset, count), 1)
        val mockRespDto = mockk<TransactionRespDto> {
            every { id } returns 1L
            every { content } returns "커피커피"
        }
        val dtoWithCount = TransactionDtoWithCount(mockRespDto, 5, 2, 3)

        every { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) } returns page
        every { transactionMapper.toDtoWithCount(transaction) } returns dtoWithCount

        // when
        transactionService.getMemberCountOffsetTransaction(memberId, count, offset, null, null, null, emptyTags)

        // then
        verify { transactionRepository.findByMemberIdWithFilters(memberId, null, null, null, null, any()) }
    }

    "거래 사진 추가 시 기존 이미지가 빈 문자열이면 빈 리스트로 처리합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())
        val newPaths = listOf("/path/to/new.jpg")

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = ""
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } returns newPaths
        every { objectMapper.writeValueAsString(newPaths) } returns """["/path/to/new.jpg"]"""
        every { transactionRepository.save(transaction) } returns transaction

        // when
        transactionService.addTransactionPhotos(memberId, transactionId, files)

        // then
        transaction.imageUrls shouldBe """["/path/to/new.jpg"]"""
    }

    "거래 사진 추가 시 newImagePaths가 null일 때 예외 처리를 확인합니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val files = listOf<MultipartFile>(mockk())

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction
        every { transactionFileUploadUtil.uploadTransactionImages(files) } throws RuntimeException("업로드 실패")
        every { transactionFileUploadUtil.deleteEmptyDateFolder() } just Runs

        // when & then
        shouldThrow<RuntimeException> {
            transactionService.addTransactionPhotos(memberId, transactionId, files)
        }

        verify { transactionFileUploadUtil.deleteEmptyDateFolder() }
    }

    "거래 사진 삭제 시 imageUrls가 빈 문자열이면 예외를 던집니다" {
        // given
        val memberId = 1L
        val transactionId = 10L
        val photoIndex = 0

        val member = TestMemberFactory.create(id = memberId)
        val category = Category("식비", false, memberId)
        val transaction = TestTransactionFactory.create(
            member = member,
            category = category,
            imageUrls = ""
        )

        every { transactionRepository.findByIdWithMemberAndCategory(transactionId) } returns transaction

        // when & then
        shouldThrow<TransactionException> {
            transactionService.deleteTransactionPhoto(memberId, transactionId, photoIndex)
        }.getErrorCode() shouldBe TransactionErrorCode.PHOTO_NOT_FOUND
    }
})