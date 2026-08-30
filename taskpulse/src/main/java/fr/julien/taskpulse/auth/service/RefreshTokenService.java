package fr.julien.taskpulse.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.julien.taskpulse.auth.entity.RefreshToken;
import fr.julien.taskpulse.auth.exception.InvalidRefreshTokenException;
import fr.julien.taskpulse.auth.repository.RefreshTokenRepository;
import fr.julien.taskpulse.security.AuthProperties;

@Service
public class RefreshTokenService {

    private static final int TOKEN_BYTES = 32;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthProperties properties;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AuthProperties properties) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.properties = properties;
    }

    public String issue(String userId) {
        return issue(userId, UUID.randomUUID().toString());
    }

    private String issue(String userId, String familyId) {
        byte[] raw = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(raw);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(raw);

        RefreshToken entity = new RefreshToken();
        entity.setTokenHash(hash(rawToken));
        entity.setUserId(userId);
        entity.setFamilyId(familyId);
        entity.setCreatedAt(Instant.now());
        entity.setExpiresAt(Instant.now().plus(properties.refreshTokenTtl()));
        refreshTokenRepository.save(entity);

        return rawToken;
    }

    public RotatedToken rotate(String rawToken) {
        RefreshToken stored = refreshTokenRepository.findByTokenHash(hash(rawToken))
                .orElseThrow(InvalidRefreshTokenException::new);

        if (stored.isRevoked()) {
            revokeFamily(stored.getFamilyId());
            throw new InvalidRefreshTokenException();
        }

        if (stored.isExpired()) {
            throw new InvalidRefreshTokenException();
        }

        String nextToken = issue(stored.getUserId(), stored.getFamilyId());
        stored.setRevokedAt(Instant.now());
        stored.setReplacedByHash(hash(nextToken));
        refreshTokenRepository.save(stored);

        return new RotatedToken(stored.getUserId(), nextToken);
    }

    public void revoke(String rawToken) {
        refreshTokenRepository.findByTokenHash(hash(rawToken)).ifPresent(stored -> {
            if (!stored.isRevoked()) {
                stored.setRevokedAt(Instant.now());
                refreshTokenRepository.save(stored);
            }
        });
    }

    public void revokeFamily(String familyId) {
        List<RefreshToken> family = refreshTokenRepository.findByFamilyId(familyId);
        Instant now = Instant.now();
        family.forEach(token -> {
            if (!token.isRevoked()) {
                token.setRevokedAt(now);
            }
        });
        refreshTokenRepository.saveAll(family);
    }

    public void revokeAllForUser(String userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findByUserId(userId);
        Instant now = Instant.now();
        tokens.forEach(token -> {
            if (!token.isRevoked()) {
                token.setRevokedAt(now);
            }
        });
        refreshTokenRepository.saveAll(tokens);
    }

    private String hash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashed);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 indisponible", exception);
        }
    }

    public record RotatedToken(String userId, String refreshToken) {
    }
}
