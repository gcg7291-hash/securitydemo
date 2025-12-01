package com.example.securitydemo.service;

import com.example.securitydemo.dto.SignupDto;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.securitydemo.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder; // 패스워드 암호화

    @Override
    public Optional<User> findByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public User register(SignupDto signupDto) {
        User user = User.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role("ROLE_USER")
                .email(signupDto.getEmail())
                .build();

        return userRepository.save(user); // 데이터베이스에 저장
    }
}
