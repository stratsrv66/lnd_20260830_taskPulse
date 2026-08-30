package fr.julien.taskpulse.auth.dto;

import java.time.Duration;

public record AuthResult(
        String accessToken,
        long expiresIn,
        String refreshToken,
        Duration refreshTokenTtl,
        UserResponse user) {

    public AuthResponse toResponse() {
        return AuthResponse.of(accessToken, expiresIn, user);
    }
}
