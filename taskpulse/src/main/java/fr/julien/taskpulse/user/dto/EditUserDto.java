package fr.julien.taskpulse.user.dto;

import fr.julien.taskpulse.user.entity.User;

public record EditUserDto(String id, String email, String password, String username) {

    public static EditUserDto fromEntity(User user) {
        return new EditUserDto(user.getId(), user.getEmail(), null, user.getUsername());
    }

    public User toEntity(String passwordHash) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        return user;
    }

    @Override
    public String toString() {
        return "EditUserDto [id=" + id + ", email=" + email + ", username=" + username + "]";
    }
}
