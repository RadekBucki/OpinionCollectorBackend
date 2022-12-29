package pl.opinion_collector.backend.logic.suggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.User;

@AllArgsConstructor
@Getter
@Setter
public class ReviewerDTO {
    private String firstName;
    private String lastName;
    private String profilePictureUrl;

    public static ReviewerDTO map(User reviewer) {
        return (reviewer != null) ? new ReviewerDTO(reviewer.getFirstName(), reviewer.getLastName(),
                reviewer.getProfilePictureUrl()) : null;
    }
}
