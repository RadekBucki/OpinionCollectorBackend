package pl.opinion_collector.backend.logic.user.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageArg {
    private String message;

    public MessageArg(String message) {
        this.message = message;
    }
}
