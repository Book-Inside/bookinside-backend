package com.api.bookinside.domain.member.service;

import com.api.bookinside.domain.member.controller.dto.JoinRequest;
import com.api.bookinside.domain.member.controller.dto.LoginRequest;
import com.api.bookinside.domain.member.controller.dto.LoginResponse;
import com.api.bookinside.domain.member.entity.Member;
import com.api.bookinside.domain.member.repository.MemberRepository;
import com.api.bookinside.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

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

    public LoginResponse login(LoginRequest request) {

        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return LoginResponse.of(jwtTokenProvider.generateToken(authentication));
    }
}
