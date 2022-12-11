package pl.opinion_collector.backend.logic.user.exception.type;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super(message);
    }
}
