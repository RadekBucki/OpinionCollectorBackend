package pl.opinion_collector.backend.logic.suggestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.opinion_collector.backend.database_communication.model.User;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String profilePictureUrl;

    public static UserDTO map(User user) {
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getProfilePictureUrl());
    }
}
