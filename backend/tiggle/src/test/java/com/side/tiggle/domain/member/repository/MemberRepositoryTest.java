package com.side.tiggle.domain.member.repository;

import com.side.tiggle.domain.member.model.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    private void clear(){
        em.flush();
        em.clear();
    }

    @AfterEach
    private void after(){
        em.clear();
    }

    @Test
    @DisplayName("회원저장_성공")
    public void 회원저장_성공() throws Exception {
        //given
        Member member = Member.builder().email("test").nickname("NickName1").birth(LocalDate.of(2020, 12, 06)).build();

        //when
        Member saveMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(saveMember.getId()).orElseThrow(() -> new RuntimeException("저장된 회원이 없습니다")); //아직 예외 클래스를 만들지 않았기에 RuntimeException으로 처리하겠습니다.

        System.out.println("findMember = " + findMember);

        assertThat(findMember).isEqualTo(saveMember);
        assertThat(findMember).isEqualTo(findMember);
    }
}