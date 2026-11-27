
package com.explore.exploreeasternindia.admin.config;

import com.explore.exploreeasternindia.admin.entity.Admin;
import com.explore.exploreeasternindia.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String username;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;

    public AdminDataInitializer(
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (adminRepository.existsByUsername(username)
                || adminRepository.existsByEmail(email)) {
            System.out.println("Admin account already exists.");
            return;
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ROLE_ADMIN");
        admin.setEnabled(true);

        adminRepository.save(admin);

        System.out.println("Initial admin account created successfully.");
    }
}
