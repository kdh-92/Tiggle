package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.req.MemberUpdateReqDto
import com.side.tiggle.domain.member.exception.MemberException
import com.side.tiggle.domain.member.exception.error.MemberErrorCode
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.member.utils.MemberFileUploadUtil
import com.side.tiggle.support.factory.TestMemberFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.Optional

class MemberServiceTest : BehaviorSpec({

    val memberRepository = mockk<MemberRepository>()
    val fileUploadUtil = mockk<MemberFileUploadUtil>(relaxed = true)
    val memberService = MemberServiceImpl(memberRepository, fileUploadUtil)

    afterEach {
        clearAllMocks()
    }

    given("profileUrl이 null인 기본 Member가 주어지면") {
        `when`("회원 정보를 조회할 때") {
            val member = TestMemberFactory.create(id = 1L)
            every { memberRepository.findById(1L) } returns Optional.of(member)

            val result = memberService.getMember(1L)

            then("MemberRespDto로 Member의 정보를 확인할 수 있어야 한다") {
                result shouldNotBe null
                result.id shouldBe 1L
                result.nickname shouldBe "test"
            }
        }

        `when`("전체 조회할 때") {
            val member1 = TestMemberFactory.create(id = 1L)
            val member2 = TestMemberFactory.create(id = 2L)
            every { memberRepository.findAll() } returns listOf(member1, member2)

            val result = memberService.getAllMember()

            then("전체 Member 목록을 반환해야 한다") {
                result.members.size shouldBe 2
                result.members.forEach {
                    it.nickname shouldNotBe null
                }
            }
        }

        `when`("nickname과 birth를 변경할 때") {
            val member = TestMemberFactory.create(id = 1L)
            val updateDto = MemberUpdateReqDto(nickname = "chgNickname", birth = LocalDate.of(1995, 5, 15))

            every { memberRepository.findById(1L) } returns Optional.of(member)
            every { memberRepository.save(any()) } returns member

            memberService.updateMember(1L, updateDto, null)

            then("변경한 Member 정보가 업데이트되어야 한다") {
                member.nickname shouldBe "chgNickname"
                member.birth shouldBe LocalDate.of(1995, 5, 15)
                verify { memberRepository.save(member) }
            }
        }
    }

    given("존재하지 않는 회원 ID가 주어지면") {
        `when`("회원 정보를 조회할 때") {
            then("MEMBER_NOT_FOUND 예외가 발생해야 한다") {
                every { memberRepository.findById(999L) } returns Optional.empty()

                shouldThrow<MemberException> {
                    memberService.getMember(999L)
                }.getErrorCode() shouldBe MemberErrorCode.MEMBER_NOT_FOUND
            }
        }

        `when`("회원 정보를 수정할 때") {
            then("MEMBER_NOT_FOUND 예외가 발생해야 한다") {
                every { memberRepository.findById(999L) } returns Optional.empty()

                shouldThrow<MemberException> {
                    memberService.updateMember(999L, MemberUpdateReqDto("test", null), null)
                }.getErrorCode() shouldBe MemberErrorCode.MEMBER_NOT_FOUND
            }
        }

        `when`("getMemberOrThrow를 호출할 때") {
            then("MEMBER_NOT_FOUND 예외가 발생해야 한다") {
                every { memberRepository.findById(999L) } returns Optional.empty()

                shouldThrow<MemberException> {
                    memberService.getMemberOrThrow(999L)
                }.getErrorCode() shouldBe MemberErrorCode.MEMBER_NOT_FOUND
            }
        }
    }

    given("프로필 이미지 파일이 함께 주어지면") {
        `when`("회원 정보를 수정할 때") {
            val member = TestMemberFactory.create(id = 1L, profileUrl = "old-profile.jpg")
            val file = mockk<MultipartFile> {
                every { isEmpty } returns false
            }

            every { memberRepository.findById(1L) } returns Optional.of(member)
            every { fileUploadUtil.deleteProfileImage("old-profile.jpg") } returns Unit
            every { fileUploadUtil.uploadProfileImage(1L, file) } returns "new-profile.jpg"
            every { memberRepository.save(any()) } returns member

            val dto = MemberUpdateReqDto(nickname = null, birth = null)
            memberService.updateMember(1L, dto, file)

            then("기존 프로필을 삭제하고 새 프로필을 업로드해야 한다") {
                verify { fileUploadUtil.deleteProfileImage("old-profile.jpg") }
                verify { fileUploadUtil.uploadProfileImage(1L, file) }
                member.profileUrl shouldBe "new-profile.jpg"
            }
        }
    }
})
