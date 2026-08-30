package fr.julien.taskpulse.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Email @Size(max = 254) String email,

        @NotBlank @Size(min = 3, max = 32) @Pattern(regexp = "^[a-zA-Z0-9._-]+$") String username,

        @NotBlank @Size(min = 12, max = 128) String password) {

    @Override
    public String toString() {
        return "RegisterRequest [email=" + email + ", username=" + username + "]";
    }
}
