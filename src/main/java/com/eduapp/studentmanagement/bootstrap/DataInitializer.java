package com.eduapp.studentmanagement.bootstrap;

import com.eduapp.studentmanagement.entity.Admin;
import com.eduapp.studentmanagement.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * On application startup, ensures exactly one admin account exists.
 * Reads the default credentials from application.properties so they can be
 * changed per-environment without touching code.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default-username}")
    private String defaultUsername;

    @Value("${app.admin.default-password}")
    private String defaultPassword;

    @Override
    public void run(String... args) {
        if (!adminRepository.existsByUsername(defaultUsername)) {
            Admin admin = Admin.builder()
                    .username(defaultUsername)
                    .password(passwordEncoder.encode(defaultPassword))
                    .role("ROLE_ADMIN")
                    .build();
            adminRepository.save(admin);
            System.out.println("Default admin account created -> username: " + defaultUsername);
        }
    }
}
