package fr.julien.taskpulse.user.dto;

import java.util.List;

public record GetUserDto(List<String> ids) {

    @Override
    public String toString() {
        return "GetUserDto [ids=" + ids + "]";
    }
}
