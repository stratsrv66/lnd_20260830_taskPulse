package fr.julien.taskpulse.user.dto;

import fr.julien.taskpulse.user.entity.User;

public record DeleteUserDto(String id) {

    public static DeleteUserDto fromEntity(User user) {
        return new DeleteUserDto(user.getId());
    }

    @Override
    public String toString() {
        return "DeleteUserDto [id=" + id + "]";
    }
}
