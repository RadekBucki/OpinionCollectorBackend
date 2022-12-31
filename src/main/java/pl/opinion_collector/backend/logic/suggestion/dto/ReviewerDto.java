package pl.opinion_collector.backend.logic.suggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.User;

@AllArgsConstructor
@Getter
@Setter
public class ReviewerDto {
    private String firstName;
    private String lastName;
    private String profilePictureUrl;

    public static ReviewerDto map(User reviewer) {
        return (reviewer != null) ? new ReviewerDto(reviewer.getFirstName(), reviewer.getLastName(),
                reviewer.getProfilePictureUrl()) : null;
    }
}
