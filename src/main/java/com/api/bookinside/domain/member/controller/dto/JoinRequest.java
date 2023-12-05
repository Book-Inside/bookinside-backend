package com.api.bookinside.domain.member.controller.dto;

import com.api.bookinside.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class JoinRequest {

    private String email;
    private String password;
    private String passwordConfirm;

    public static Member of(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
