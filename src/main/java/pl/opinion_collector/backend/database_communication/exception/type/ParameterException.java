package pl.opinion_collector.backend.database_communication.exception.type;

public class ParameterException extends RuntimeException{
    public ParameterException(String message) {
        super(message);
    }
}