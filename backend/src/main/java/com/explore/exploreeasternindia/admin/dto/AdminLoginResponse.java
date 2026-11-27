
package com.explore.exploreeasternindia.admin.dto;

public record AdminLoginResponse(
        String token,
        String tokenType,
        String username,
        String role
) {}
