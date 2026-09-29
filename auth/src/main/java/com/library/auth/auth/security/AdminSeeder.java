package com.library.auth.auth.security;

import com.library.auth.auth.model.Member;
import com.library.auth.auth.model.MemberRole;
import com.library.auth.auth.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String email;
    private final String password;
    private final String fullname = "admin";

    public AdminSeeder(MemberRepository memberRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${admin.username:}") String username,
                       @Value("${admin.email:}") String email,
                       @Value("${admin.password:}") String password) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (email.isBlank() || password.isBlank()) {
            log.info("ADMIN_EMAIL/ADMIN_PASSWORD not set, skipping admin seed");
            return;
        }
        if (memberRepository.existsByEmail(email)) {
            return;
        }
        Member admin = new Member(username, passwordEncoder.encode(password), fullname, email, MemberRole.ADMIN);
        memberRepository.save(admin);
        log.info("Created admin user {}", email);
    }
}