package pl.opinion_collector.backend.logic.exception.type;

public class InvalidDataIdException extends RuntimeException {

    public InvalidDataIdException(String message) {
        super(message);
    }

    public InvalidDataIdException(Exception e) {
        super(e);
    }
}
