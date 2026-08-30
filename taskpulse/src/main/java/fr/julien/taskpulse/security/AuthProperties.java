package fr.julien.taskpulse.security;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "taskpulse.auth")
public record AuthProperties(
        String issuer,
        String jwtSecret,
        Duration accessTokenTtl,
        Duration refreshTokenTtl,
        String refreshCookieName,
        String refreshCookiePath,
        boolean refreshCookieSecure,
        String refreshCookieSameSite,
        List<String> allowedOrigins) {
}
