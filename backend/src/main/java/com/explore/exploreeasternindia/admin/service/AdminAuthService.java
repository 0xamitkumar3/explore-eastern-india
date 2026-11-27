package com.explore.exploreeasternindia.admin.service;

import com.explore.exploreeasternindia.admin.dto.AdminLoginRequest;
import com.explore.exploreeasternindia.admin.dto.AdminLoginResponse;
import com.explore.exploreeasternindia.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdminAuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AdminLoginResponse login(AdminLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String username = authentication.getName();

        String role = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_ADMIN");

        String token = jwtService.generateToken(username, role);

        return new AdminLoginResponse(
                token,
                "Bearer",
                username,
                role
        );
    }
}
