package com.explore.exploreeasternindia.admin.controller;

import com.explore.exploreeasternindia.admin.dto.AdminLoginRequest;
import com.explore.exploreeasternindia.admin.dto.AdminLoginResponse;
import com.explore.exploreeasternindia.admin.service.AdminAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
            @Valid @RequestBody AdminLoginRequest request
    ) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }
}
