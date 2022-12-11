package pl.opinion_collector.backend.logic.user.exception.type;

public class ParameterException extends RuntimeException{
    public ParameterException(String message) {
        super(message);
    }
}