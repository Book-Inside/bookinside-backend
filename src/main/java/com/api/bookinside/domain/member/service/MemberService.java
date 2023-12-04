package com.api.bookinside.domain.member.service;

import com.api.bookinside.domain.member.controller.dto.JoinRequest;
import com.api.bookinside.domain.member.entity.Member;
import com.api.bookinside.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public void join(JoinRequest request) {
        // 가입된 회원이 있는지 체크
        memberRepository.findByEmail(request.getEmail()).ifPresent(it -> {
            throw new RuntimeException("%s not founded".formatted(it.getEmail()));
        });

        // 비밀 번호 일치 여부
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new RuntimeException("password not matched!");
        }

        Member member = JoinRequest.of(request.getEmail(), encoder.encode(request.getPassword()));
        memberRepository.save(member);
    }
}
