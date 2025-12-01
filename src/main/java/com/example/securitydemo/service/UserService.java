package com.example.securitydemo.service;

import com.example.securitydemo.dto.SignupDto;
import com.example.securitydemo.entity.User;

import java.util.Optional;

public interface UserService {

    // 사용자 조회
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    // username 중복확인
    boolean existsByUsername(String username);

    // email 중복확인
    boolean existsByEmail(String email);

    // 회원가입
    User register(SignupDto signupDto);
}
