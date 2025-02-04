package com.practice.domain.user.service;

import com.practice.domain.user.Role;
import com.practice.domain.user.User;
import com.practice.domain.user.dto.UserSignUpDto;
import com.practice.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("already exist email.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("already exist nickname.");
        }

        User user = User.builder()
            .email(userSignUpDto.getEmail())
            .password(userSignUpDto.getPassword())
            .nickname(userSignUpDto.getNickname())
            .age(userSignUpDto.getAge())
            .city(userSignUpDto.getCity())
            .role(Role.USER) // 자체 로그인은 ROLE_USER
            .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
}
