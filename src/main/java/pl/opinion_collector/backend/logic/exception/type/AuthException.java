package pl.opinion_collector.backend.logic.exception.type;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super(message);
    }
}
