package pl.opinion_collector.backend.logic.user.dto;

import pl.opinion_collector.backend.database_communication.model.User;

public class Mapper {
    public UserDto mapUser(User user) {
        return new UserDto.UserDtoBuilder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isAdmin(user.getAdmin())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }
    public UserWithIdDto mapUserWithId(User user) {
        return new UserWithIdDto.UserWithIdDtoBuilder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isAdmin(user.getAdmin())
                .profilePictureUrl(user.getProfilePictureUrl())
                .build();
    }

}
