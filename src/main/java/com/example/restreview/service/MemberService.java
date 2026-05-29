package com.example.restreview.service;

import com.example.restreview.entity.Member;
import com.example.restreview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void signup(String name, String email, String password) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("This email is already in use.");
        }

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        memberRepository.save(member);
    }

    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElse(null);
    }
}