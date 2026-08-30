package fr.julien.taskpulse.auth.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "refreshTokens")
@Data
public class RefreshToken {
    @Id
    private String id;

    @Indexed(unique = true)
    private String tokenHash;

    @Indexed
    private String userId;

    @Indexed
    private String familyId;

    @Indexed(expireAfterSeconds = 0)
    private Instant expiresAt;

    private Instant createdAt;
    private Instant revokedAt;
    private String replacedByHash;

    public boolean isRevoked() {
        return revokedAt != null;
    }

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }
}
