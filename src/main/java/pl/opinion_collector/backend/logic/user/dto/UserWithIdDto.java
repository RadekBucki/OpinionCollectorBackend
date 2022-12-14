package pl.opinion_collector.backend.logic.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWithIdDto extends UserDto {
    @ApiModelProperty(notes = "User id returned to the user", example = "1")
    private Long id;

    @Builder(builderMethodName = "UserWithIdBuilder")
    public UserWithIdDto(Long id, String firstName, String lastName,
                         String email, Boolean isAdmin, String profilePictureUrl) {
        super(firstName, lastName, email, isAdmin, profilePictureUrl);
        this.id = id;
    }
}
