package pl.opinion_collector.backend.logic.user.exception.type;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

}
