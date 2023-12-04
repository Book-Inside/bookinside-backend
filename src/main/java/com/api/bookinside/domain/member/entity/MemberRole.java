package com.api.bookinside.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    MEMBER("회원"),
    ADMIN("관리자"),;

    private final String description;
}
