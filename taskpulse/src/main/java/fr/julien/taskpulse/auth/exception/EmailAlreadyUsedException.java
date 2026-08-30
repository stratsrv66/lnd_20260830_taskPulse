package fr.julien.taskpulse.auth.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Cet email est deja utilise");
    }
}
