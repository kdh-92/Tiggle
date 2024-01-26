package com.side.tiggle.domain.member.service

import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
import com.side.tiggle.domain.member.dto.service.MemberDto
import com.side.tiggle.domain.member.model.Member
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest(
    private val memberService: MemberService
) : BehaviorSpec({

    given("profilUrl이 null인 기본 Member가 주어지면") {
        val member = Member("email", null, "nickname", LocalDate.of(2020, 1, 20))

        `when`("회원가입할 때") {
            val memberResponseDto: MemberResponseDto = memberService.createMember(MemberDto(null, member.email, member.profileUrl, member.nickname, member.birth))

            then("memberResponseDto로 Member의 정보를 확인할 수 있어야 한다.") {
                memberResponseDto.email shouldBe member.email
                memberResponseDto.nickname shouldBe member.nickname
                memberResponseDto.birth shouldBe member.birth
            }

            `when`("단일 조회할 때") {
                val getMemberResponseDto: MemberResponseDto = memberService.getMember(memberResponseDto.id)

                then("memberResponseDto로 Member의 정보를 확인할 수 있어야 한다.") {
                    memberResponseDto.email shouldBe getMemberResponseDto.email
                    memberResponseDto.nickname shouldBe getMemberResponseDto.nickname
                    memberResponseDto.birth shouldBe getMemberResponseDto .birth
                }
            }

            `when`("전체 조회할 때") {
                val getMemberResponseDtoList: List<MemberResponseDto> = memberService.getAllMember()

                then("전체 Member의 각 email, nickname, birth은 null이 아니어야 한다.") {
                    for (i in getMemberResponseDtoList) {
                        i.email shouldNotBe null
                        i.nickname shouldNotBe null
                        i.birth shouldNotBe null
                    }
                }
            }

            `when`("MemberId 1번 Member의 profileUrl은 수정하지 않고, email, nickname 변경할 때") {
                val chgMemberDto = MemberDto(memberResponseDto.id, "chg@email.com", null, "chgNickname", member.birth)
                val updateMemberResponseDto: MemberResponseDto = memberService.updateMember(memberResponseDto.id, chgMemberDto, null)
                val chgMemberResponseDto: MemberResponseDto = memberService.getMember(memberResponseDto.id)

                then("변경한 Member id 조회 시 조회 정보와 변경 정보가 동일해야 한다.") {
                    chgMemberResponseDto shouldBe updateMemberResponseDto
                }
            }
        }
    }
})
