package pl.opinion_collector.backend.database_communication.exception.type;

public class InvalidBusinessArgumentException extends RuntimeException{
    public InvalidBusinessArgumentException(String message) {
        super(message);
    }
}