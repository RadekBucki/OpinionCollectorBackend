package pl.opinion_collector.backend.logic.user.exception.type;

public class InvalidBusinessArgumentException extends RuntimeException{
    public InvalidBusinessArgumentException(String message) {
        super(message);
    }
}