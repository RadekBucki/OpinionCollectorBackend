package pl.opinion_collector.backend.exception.type;

public class ParameterException extends RuntimeException{
    public ParameterException(String message) {
        super(message);
    }
}