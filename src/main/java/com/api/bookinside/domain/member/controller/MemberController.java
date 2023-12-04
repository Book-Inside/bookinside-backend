package com.api.bookinside.domain.member.controller;

import com.api.bookinside.domain.member.controller.dto.JoinRequest;
import com.api.bookinside.domain.member.service.MemberService;
import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequest request) {
        memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("success join!");
    }
}
