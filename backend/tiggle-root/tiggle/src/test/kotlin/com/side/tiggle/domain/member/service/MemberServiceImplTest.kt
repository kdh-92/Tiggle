package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.req.MemberCreateReqDto
import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.exception.MemberException
import com.side.tiggle.domain.member.exception.error.MemberErrorCode
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.member.utils.MemberFileUploadUtil
import com.side.tiggle.support.factory.TestMemberFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.Optional

class MemberServiceImplTest : StringSpec({

    val memberRepository = mockk<MemberRepository>()
    val fileUploadUtil = mockk<MemberFileUploadUtil>(relaxed = true)

    val memberService = MemberServiceImpl(memberRepository, fileUploadUtil)

    beforeTest {
        clearMocks(memberRepository, fileUploadUtil, answers = false)
    }

    "회원 생성에 성공한다" {
        val dto = mockk<MemberCreateReqDto> {
            every { toEntity() } returns mockk()
        }

        every { memberRepository.save(any()) } returns mockk()

        memberService.createMember(dto)

        verify { memberRepository.save(any()) }
    }

    "회원 정보를 조회한다 - 존재하는 경우" {
        val member = TestMemberFactory.create(id = 1L)

        every { memberRepository.findById(1L) } returns Optional.of(member)

        val result = memberService.getMember(1L)

        result shouldNotBe null
        verify { memberRepository.findById(1L) }
    }

    "회원 정보를 조회할 때 존재하지 않으면 예외 발생" {
        every { memberRepository.findById(1L) } returns Optional.empty()

        shouldThrow<MemberException> {
            memberService.getMember(1L)
        }.getErrorCode() shouldBe MemberErrorCode.MEMBER_NOT_FOUND
    }

    "getMemberOrThrow - 회원이 없으면 예외 발생" {
        every { memberRepository.findById(1L) } returns Optional.empty()

        shouldThrow<MemberException> {
            memberService.getMemberOrThrow(1L)
        }.getErrorCode() shouldBe MemberErrorCode.MEMBER_NOT_FOUND
    }

    "getMemberOrThrow - 회원이 존재하면 MemberInfo를 반환" {
        val member = TestMemberFactory.create(id = 1L)

        every { memberRepository.findById(1L) } returns Optional.of(member)

        val result = memberService.getMemberOrThrow(1L)

        result shouldNotBe null
        verify { memberRepository.findById(1L) }
    }

    "getMemberReference는 프록시 객체를 반환한다" {
        val member = mockk<Member>()
        every { memberRepository.getReferenceById(1L) } returns member

        val result = memberService.getMemberReference(1L)

        result shouldBe member
    }

    "전체 회원 목록을 조회한다" {
        val member1 = TestMemberFactory.create(id = 1L)
        val member2 = TestMemberFactory.create(id = 2L)

        every { memberRepository.findAll() } returns listOf(member1, member2)

        val result = memberService.getAllMember()

        result.members.size shouldBe 2
        result.members.first().id shouldBe 1L
    }

    "회원 정보를 수정한다 - 프로필 이미지 포함" {
        val file = mockk<MultipartFile> {
            every { isEmpty } returns false
        }
        val member = TestMemberFactory.create(id = 1L, profileUrl = "old.jpg")
        val dto = MemberUpdateReqDto(nickname = "newNick", birth = LocalDate.of(1990, 1, 1))

        every { memberRepository.findById(1L) } returns Optional.of(member)
        every { fileUploadUtil.deleteProfileImage("old.jpg") } returns Unit
        every { fileUploadUtil.uploadProfileImage(1L, file) } returns "new.jpg"
        every { memberRepository.save(any()) } returns member

        memberService.updateMember(1L, dto, file)

        verify { fileUploadUtil.uploadProfileImage(1L, file) }
        verify { memberRepository.save(any()) }

        member.nickname shouldBe "newNick"
        member.birth shouldBe LocalDate.of(1990, 1, 1)
        member.profileUrl shouldBe "new.jpg"
    }

    "회원 정보를 수정한다 - 파일 없이 nickname, birth만 수정" {
        val file = mockk<MultipartFile> {
            every { isEmpty } returns true
        }
        val member = TestMemberFactory.create(id = 1L)
        val dto = MemberUpdateReqDto(nickname = "updatedNick", birth = LocalDate.of(1991, 2, 2))

        every { memberRepository.findById(1L) } returns Optional.of(member)
        every { memberRepository.save(any()) } returns member

        memberService.updateMember(1L, dto, file)

        verify(exactly = 0) { fileUploadUtil.uploadProfileImage(any(), any()) }
        verify { memberRepository.save(member) }

        member.nickname shouldBe "updatedNick"
        member.birth shouldBe LocalDate.of(1991, 2, 2)
    }

    "회원 정보를 수정한다 - 이미지 파일만 수정, nickname과 birth는 null" {
        val file = mockk<MultipartFile> {
            every { isEmpty } returns false
        }
        val member = TestMemberFactory.create(id = 1L, profileUrl = "before.jpg")
        val dto = MemberUpdateReqDto(nickname = null, birth = null)

        every { memberRepository.findById(1L) } returns Optional.of(member)
        every { fileUploadUtil.deleteProfileImage("before.jpg") } returns Unit
        every { fileUploadUtil.uploadProfileImage(1L, file) } returns "after.jpg"
        every { memberRepository.save(any()) } returns member

        memberService.updateMember(1L, dto, file)

        verify { fileUploadUtil.uploadProfileImage(1L, file) }
        verify { memberRepository.save(member) }

        member.profileUrl shouldBe "after.jpg"
    }

    "회원 정보를 수정하지 않는다 - 파일 없고 nickname, birth 모두 null" {
        val file: MultipartFile? = null
        val member = TestMemberFactory.create(id = 1L)
        val dto = MemberUpdateReqDto(nickname = null, birth = null)

        every { memberRepository.findById(1L) } returns Optional.of(member)

        memberService.updateMember(1L, dto, file)

        verify(exactly = 0) { memberRepository.save(any()) }
        verify(exactly = 0) { fileUploadUtil.uploadProfileImage(any(), any()) }
    }


    "회원 수정 시 member가 없으면 예외 발생" {
        every { memberRepository.findById(1L) } returns Optional.empty()

        shouldThrow<MemberException> {
            memberService.updateMember(1L, MemberUpdateReqDto("test", LocalDate.now()), null)
        }.getErrorCode() shouldBe MemberErrorCode.MEMBER_NOT_FOUND
    }
})
