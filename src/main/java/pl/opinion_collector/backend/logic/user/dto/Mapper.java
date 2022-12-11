package pl.opinion_collector.backend.logic.user.dto;

import pl.opinion_collector.backend.logic.user.model.AppUser;

public class Mapper {

    public UserDto mapUser(AppUser userWrapper) {
        return new UserDto.UserDtoBuilder()
                .firstName(userWrapper.getFirstName())
                .lastName(userWrapper.getLastName())
                .email(userWrapper.getEmail())
                .isAdmin(userWrapper.isAdmin())
                .profilePictureUrl(userWrapper.getPictureProfileUrl())
                .build();
    }
}
