package fr.julien.taskpulse.auth.exception;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException() {
        super("Ce nom d'utilisateur est deja utilise");
    }
}
