package com.example.restreview.service;

import com.example.restreview.entity.Member;
import com.example.restreview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void signup(String name, String email, String password) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("This email is already in use.");
        }

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }

    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(password, m.getPassword()))
                .orElse(null);
    }
}