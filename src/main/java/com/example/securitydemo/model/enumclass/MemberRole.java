package com.example.securitydemo.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    USER(0, "사용자", "일반 사용자 상태"),
    ADMIN(1, "관리자", "시스템 관리자 상태")
    ;

    private Integer id;
    private String title;
    private String description;
}
