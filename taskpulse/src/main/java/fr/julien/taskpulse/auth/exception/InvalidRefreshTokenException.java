package fr.julien.taskpulse.auth.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("Session expiree, reconnexion necessaire");
    }
}
