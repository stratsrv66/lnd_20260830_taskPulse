package fr.julien.taskpulse.auth.dto;

import java.util.Set;

import fr.julien.taskpulse.user.entity.User;

public record UserResponse(
        String id,
        String email,
        String username,
        Set<String> roles,
        String createdAt) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRoles(),
                user.getCreatedAt());
    }
}
