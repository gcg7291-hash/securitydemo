package com.example.securitydemo.config;

import com.example.securitydemo.entity.User;
import com.example.securitydemo.repository.UserRepository;
import com.example.securitydemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()           // entity user 에 @Builder 사용해서
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .role("ROLE_USER")
                .build();

        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);
        userRepository.save(admin);

        System.out.println("user 데이터 생성완료");
    }
}
