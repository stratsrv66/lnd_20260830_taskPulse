package fr.julien.taskpulse.user.dto;

import fr.julien.taskpulse.user.entity.User;

public record CreateUserDto(
        String email,
        String password,
        String username) {

    public User toEntity(String passwordHash) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        return user;
    }

    @Override
    public String toString() {
        return "CreateUserDto [email=" + email + ", username=" + username + "]";
    }
}
