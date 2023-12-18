package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


public class MemberServiceTest {
    MemoryMemberRepository memberRepository;
    MemberService memberService;
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("spring");
        //where
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(),findMember.getName());
    }
    @Test
    public void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("spring1");
        Member member2 = new Member();
        member2.setName("spring1");

        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}
