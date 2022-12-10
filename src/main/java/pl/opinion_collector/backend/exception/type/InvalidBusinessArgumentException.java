package pl.opinion_collector.backend.exception.type;

public class InvalidBusinessArgumentException extends RuntimeException{
    public InvalidBusinessArgumentException(String message) {
        super(message);
    }
}