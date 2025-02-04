package com.practice.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자체 로그인 회원가입 시 사용될 RequestBody
 */
@NoArgsConstructor
@Getter
public class UserSignUpDto {

    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
}